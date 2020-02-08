package com.example.jetty;

import java.util.function.Supplier;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.glassfish.hk2.api.Factory;
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
 * Don't annotate this class by @Singleton unless get() or provide() is called only one time, in
 * other words to avoid all "@Inject EntityManager em" are same object. 
 * </p>
 * @author atsushi.hondoh
 */
@Service
public class MyEntityManagerSupplier implements Factory<EntityManager>, Supplier<EntityManager> {
    
    /**
     * EntityManagerFactory.
     * EntityManagerFactory of the Eclipselink is thread-safe. But EntityManager is
     * not thread-safe.
     * cf. https://docs.oracle.com/cd/E19798-01/821-1841/bnbqy/index.html
     */
    private static final EntityManagerFactory EMFACTORY = Persistence.createEntityManagerFactory("JettyExamPU");

    @Override
    public EntityManager provide() {
        return EMFACTORY.createEntityManager();
    }

    @Override
    public void dispose(EntityManager em) {
        try {
            em.close();
        } catch (Throwable th) {
            // do nothing.
            th = null;
        }
    }

    @Override
    public EntityManager get() {
        return provide();
    }
}
