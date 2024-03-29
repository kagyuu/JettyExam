package com.example.jetty.logic;

import com.example.jetty.MyConst;
import com.example.jetty.entity.AppBinaryEntity;
import com.example.jetty.entity.ContainAppEntity;
import com.example.jetty.entity.ResourceEntity;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;

/**
 * Service Class for ContainAppEntity.
 * @author atsushi.hondoh
 */
@Slf4j
@Service
@Transactional
@NoArgsConstructor
public class ContainAppService {
    @Inject
    private EntityManager em;
    
    @Inject
    private MyConst myconst;
    
    public ContainAppEntity create(Map<String, Object> inJSON) throws Exception {
        Long resourceId = Long.parseLong(inJSON.get("resource").toString());
        Long appId = Long.parseLong(inJSON.get("app").toString());
        ResourceEntity resourceEntity = em.find(ResourceEntity.class, resourceId);
        AppBinaryEntity appBinaryeEntity = em.find(AppBinaryEntity.class, appId);
        
        ContainAppEntity containAppEntity = new ContainAppEntity();
        containAppEntity.setResource(resourceEntity);
        containAppEntity.setApp(appBinaryeEntity);
        em.persist(containAppEntity);

        // Trap?
        // We have to make relations not only from added new entity (@OneToMany) ,
        // but also from parent entities (@ManyToOne).
        resourceEntity.getContains().add(containAppEntity);
        appBinaryeEntity.getContainedBy().add(containAppEntity);
        em.merge(resourceEntity);
        em.merge(appBinaryeEntity);
        
        em.flush();
        
        return containAppEntity;
    }
    
    public ContainAppEntity findById(Long id) {
        return em.find(ContainAppEntity.class, id);
    }
}
