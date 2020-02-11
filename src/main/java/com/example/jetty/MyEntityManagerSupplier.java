package com.example.jetty;

import java.util.function.Supplier;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;

/**
 * EntityManagerSupplier for the hk2.
 * You can @Inject this in business class.
 * <p>
 * In v2.27 the hk2 replaced the inteterface of Factory Classes 
 * from the hk2's Factory to the java.util's Supplier.
 * But the current Jersey v2.30 uses the hk2 v2.6, so I made it implements 
 * both Factory interface and Supplier interface.
 * <br>
 * cf. https://jersey.github.io/documentation/latest/user-guide.html#mig-2.27-injection-manager
 * </p>
 * <p>
 * I know @RequestScoperd looks like suitable. But Jersey/HK2 don't support @Inject
 * in Interceprtor. I have to manage the instance of the em by own hand.
 * </p>
 * @author atsushi.hondoh
 */
@Service
@PerLookup
public class MyEntityManagerSupplier implements Factory<EntityManager>, Supplier<EntityManager> {
    
    /**
     * EntityManagerFactory.
     * EntityManagerFactory of the Eclipselink is thread-safe. But EntityManager is
     * not thread-safe.
     * cf. https://docs.oracle.com/cd/E19798-01/821-1841/bnbqy/index.html
     */
    private static final EntityManagerFactory EM_FACTORY = Persistence.createEntityManagerFactory("JettyExamPU");
    
    /**
     * ThreadLocal Entity Manager Storage.
     */
    private static final ThreadLocal<EntityManager> EM_STORAGE = new ThreadLocal<>();

    /**
     * Get ThreadLocal Entity Manager.
     * To transaction management automatically, We made MyTransactionInterceptor.
     * But Jersey/HK2 don't support injection in interceptors. So We have to 
     * manage the EntityManager by ThreadLocal.
     * @return ThreadLocal Entity Manager
     */
    public static EntityManager getThreadLocalEntityManager() {
        EntityManager em = EM_STORAGE.get();
        if (null == em) {
            em = EM_FACTORY.createEntityManager();
            EM_STORAGE.set(em);
        }
        return em;
    }
    
    /**
     * Remove ThreadLocal Entity Manager.
     */
    public static void removeThreadLocalEntityManager(){
        EM_STORAGE.remove();
    }
    
    
    @Override
    public EntityManager provide() {
        return getThreadLocalEntityManager();
    }

    @Override
    public void dispose(EntityManager em) {
        // do nothing.
    }

    @Override
    public EntityManager get() {
        return getThreadLocalEntityManager();
    }
}
