package com.example.jetty.logic;

import com.example.jetty.MyConst;
import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;

/**
 * Service Class.
 * @author atsushi.hondoh
 */
@Slf4j
@Service
@NoArgsConstructor
public class HelloService implements Serializable {
    @Inject
    private EntityManager em;
    
    @Inject
    private MyConst myconst;
    
    public String hello(){
        
        log.info("********** EM'S HASH CODE : {}", em.hashCode());
        log.info("********** myconst foo={}", myconst.get("foo").hashCode());
        
        em.getTransaction().begin();
        em.getTransaction().commit();
        
        return "HLLO";
    }
}
