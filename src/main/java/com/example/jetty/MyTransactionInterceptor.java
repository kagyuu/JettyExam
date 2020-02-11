package com.example.jetty;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Transaction Interceptor.
 * @author atsushi.hondoh
 */
@NoArgsConstructor
@Slf4j
public class MyTransactionInterceptor implements MethodInterceptor {
    
    @Override
    public Object invoke(MethodInvocation target) throws Throwable {
        EntityManager em = MyEntityManagerSupplier.getThreadLocalEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            log.info("---- BEGIN {}", em.hashCode());
            Object returnValue = target.proceed();
            tx.commit();
            log.info("---- COMMIT {}", em.hashCode());
            return returnValue;
        } catch (Throwable th) {
            tx.rollback();
            log.info("---- ROLLBACK {}", em.hashCode());
            throw th;
        } finally {
            MyEntityManagerSupplier.removeThreadLocalEntityManager();
            em.close();
        }
    }   
}
