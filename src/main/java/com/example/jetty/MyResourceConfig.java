package com.example.jetty;

import com.example.jetty.resources.AppBinaryResource;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.annotations.Service;

/**
 * ResourceConfig for Jersey-servlet on Jetty.
 *
 * @author atsushi.hondoh
 */
@Slf4j
public class MyResourceConfig extends ResourceConfig {

    @SuppressWarnings("unchecked")
    public MyResourceConfig() {
        // Jersey's REST-API classes.
        packages(
            "jersey.config.server.provider.packages"
            , getPackageName(AppBinaryResource.class)
        );

        // hk2's @Inject classes.
        // I wrote automatically lookup thease classes that have @Service during runtime.
        // cf. The hk2 has code generator.
        //     https://javaee.github.io/hk2/inhabitant-generator.html
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                try ( ScanResult scanResult = new ClassGraph()
                        .enableAllInfo()
                        .whitelistPackages(getPackageName(this.getClass()))
                        .scan()) {

                    scanResult.getClassesWithAnnotation(Service.class.getCanonicalName()).forEach(clazzInfo -> {
                        Class clazz = clazzInfo.loadClass();
                        try {
                           
                            // Scope Annotations
                            // @PerLookup     : @Inject indicates different instance for every injection.
                            // @Singleton     : @Inject indicates the same instance during the app during start to end.
                            // @Immediate     : The scope is same as the @Singleton. @Immediate is instantiateed when 
                            //                  the app startup while @Singleton is do when called.
                            // @RequestScoped : @Inject indicates the same instance during a request to response.
                            Class scope = PerLookup.class; // default scope
                            for (Class candidate : new Class[]{PerLookup.class, Singleton.class, Immediate.class, RequestScoped.class}) {
                                scope = (null == clazz.getAnnotation(candidate)) ? scope : candidate;
                            }
                            
                            if (clazzInfo.implementsInterface(Supplier.class.getCanonicalName())) {
                                Method getMethod = clazz.getMethod("get");
                                Class toClazz = getMethod.getReturnType();
                                log.info("register factory {} that provides {}. scope {}.", clazz.getName(), toClazz.getName(), scope.getName());
                                bindFactory(clazz).to(toClazz).in(scope);
                            } else if (clazzInfo.implementsInterface(Factory.class.getCanonicalName())) {
                                Method provideMethod = clazz.getMethod("provide");
                                Class toClazz = provideMethod.getReturnType();
                                log.info("register factory {} that provides {}. scope {}.", clazz.getName(), toClazz.getName(), scope.getName());
                                bindFactory(clazz).to(toClazz).in(scope);
                            } else if (clazzInfo.implementsInterface(InterceptionService.class.getCanonicalName())) {
                                log.info("register intercepter {} . scope {}.", clazz.getName(), scope.getName());
                                bind(clazz).to(InterceptionService.class).in(scope);
                            } else {
                                log.info("register service {}. scope {}.", clazz.getName(), scope.getName());
                                bindAsContract(clazz).in(scope);
                            }

                        } catch (NoSuchMethodException | SecurityException ex) {
                            log.error("Can't register object into the hk2!", ex);
                        }
                    });
                }
            }
        });
    }
    
    /**
     * Get package name of clazz.
     * The "class#getPackageName()" is not in Java8 environment.
     * @param clazz class
     * @return package name
     */
    private String getPackageName(Class clazz) {
        return clazz.getName().replace("." + clazz.getSimpleName(), "");
    }
}
