package com.example.jetty;

import com.example.jetty.resources.HelloResource;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.annotations.Service;

/**
 * ResourceConfig for Jersey-servlet on Jetty.
 *
 * @author atsushi.hondoh
 */
@Slf4j
public class MyResourceConfig extends ResourceConfig {

    public MyResourceConfig() {
        // Jersey's REST-API classes.
        packages("jersey.config.server.provider.packages", HelloResource.class.getPackageName());

        // hk2's @Inject classes.
        // I wrote automatically lookup thease classes that have @Service during runtime.
        // cf. The hk2 has code generator.
        //     https://javaee.github.io/hk2/inhabitant-generator.html
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                try ( ScanResult scanResult = new ClassGraph()
                        .enableAllInfo()
                        .whitelistPackages(this.getClass().getPackageName())
                        .scan()) {

                    scanResult.getClassesWithAnnotation(Service.class.getCanonicalName()).forEach(clazzInfo -> {
                        Class clazz = clazzInfo.loadClass();
                        try {
                            Annotation scopeAnnotation = clazz.getAnnotation(Singleton.class);

                            if (clazzInfo.implementsInterface(Supplier.class.getCanonicalName())) {
                                Method getMethod = clazz.getMethod("get");
                                Class toClazz = getMethod.getReturnType();
                                if (null == scopeAnnotation) {
                                    log.info("register factory {} that provides {}.", clazz.getName(), toClazz.getName());
                                    bindFactory(clazz).to(toClazz);
                                } else {
                                    log.info("register factory {} that provides {}. scope {}.", clazz.getName(), toClazz.getName(), scopeAnnotation);
                                    bindFactory(clazz).to(toClazz).in(scopeAnnotation);
                                }
                            } else if (clazzInfo.implementsInterface(Factory.class.getCanonicalName())) {
                                Method provideMethod = clazz.getMethod("provide");
                                Class toClazz = provideMethod.getReturnType();
                                if (null == scopeAnnotation) {
                                    log.info("register factory {} that provides {}.", clazz.getName(), toClazz.getName());
                                    bindFactory(clazz).to(toClazz);
                                } else {
                                    log.info("register factory {} that provides {}. scope {}.", clazz.getName(), toClazz.getName(), scopeAnnotation);
                                    bindFactory(clazz).to(toClazz).in(scopeAnnotation);
                                }
                            } else {
                                if (null == scopeAnnotation) {
                                    log.info("register service {}.", clazz.getName());
                                    bindAsContract(clazz);
                                } else {
                                    log.info("register service {}. scope {}.", clazz.getName(), scopeAnnotation);
                                    bindAsContract(clazz).in(scopeAnnotation);
                                }
                            }

                        } catch (NoSuchMethodException | SecurityException ex) {
                            log.error("Can't register object into the hk2!", ex);
                        }
                    });
                }
            }
        });
    }
}
