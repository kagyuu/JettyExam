package com.example.jetty.test;

import com.example.jetty.MyResourceConfig;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;

/**
 * Test class for MyResourceConfig.
 *
 * @author atsushihondoh
 */
public class MyResourceConfigTest {

    @Test
    public void testGetPackagename() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MyResourceConfig obj = new MyResourceConfig();
        Method method = obj.getClass().getDeclaredMethod("getPackageName", Class.class);
        method.setAccessible(true);
        
        Object res = method.invoke(obj, MyResourceConfigTest.class);
        
        assertThat(res, is("com.example.jetty.test"));
    }
}
