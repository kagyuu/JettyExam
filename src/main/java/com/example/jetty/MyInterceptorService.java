package com.example.jetty;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.internal.StarFilter;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author atsushi.hondoh
 */
@Slf4j
@Service
@Singleton
public class MyInterceptorService implements InterceptionService {
            
    @Override
    public Filter getDescriptorFilter() {
        // check all methods.
        return StarFilter.getDescriptorFilter();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MethodInterceptor> getMethodInterceptors(Method method) {
        List<MethodInterceptor> interceptors = new ArrayList<>();
        Class clazz = method.getDeclaringClass();
        
        // If the method or the class covers the method has @Transctional annotation, apply MyTransactionInterceptor.
        if (method.isAnnotationPresent(Transactional.class) || clazz.isAnnotationPresent(Transactional.class)) {
            MethodInterceptor txInterceptor = new MyTransactionInterceptor();
            interceptors.add(txInterceptor);
        }
        
        return interceptors;        
    }

    @Override
    public List<ConstructorInterceptor> getConstructorInterceptors(Constructor<?> arg0) {
        // Not apply interceptor to constructors.
        return Collections.emptyList();
    }
    
}
