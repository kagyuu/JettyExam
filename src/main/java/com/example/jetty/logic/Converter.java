package com.example.jetty.logic;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Map to Bean converter.
 * @author atsushi.hondoh
 */
@Slf4j
public class Converter {

    public static Object jsonToBean(Map<String, Object> inJSON, Object bean) 
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        
        for (Map.Entry<String, Object> entry : inJSON.entrySet()) {
            String propName = entry.getKey();
            Object value = inJSON.get(propName);

            PropertyDescriptor nameProp = new PropertyDescriptor(propName, bean.getClass());
            Method getter = nameProp.getReadMethod();
            Method setter = nameProp.getWriteMethod();

            setter.invoke(bean, value);
        }
        
        return bean;
    }
}
