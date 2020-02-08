package com.example.jetty;

import java.util.ResourceBundle;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;

/**
 * Const variables.
 * Read app.properties on the classpath.
 * @author atsushi.hondoh
 */
@Singleton
@Service
public class MyConst {
    /**
     * Singleton ResourceBunle.
     * This variable is singleton too becase MyConst is @Singleton.
     */
    private final ResourceBundle RB = ResourceBundle.getBundle("app");
    
    /**
     * Get value from key.
     * @param key key
     * @return value
     */
    public String get(String key) {
        return RB.getString(key);
    }
}
