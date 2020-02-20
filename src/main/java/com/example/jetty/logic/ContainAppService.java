package com.example.jetty.logic;

import com.example.jetty.MyConst;
import com.example.jetty.entity.AppBinaryEntity;
import com.example.jetty.entity.ContainAppEntity;
import com.example.jetty.entity.ResourceEntity;
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
    
    public ContainAppEntity create(Long resourceId, Long appId) throws Exception {
        ResourceEntity resourceEntity = em.find(ResourceEntity.class, resourceId);
        AppBinaryEntity appBinaryeEntity = em.find(AppBinaryEntity.class, appId);
        
        ContainAppEntity containAppEntity = new ContainAppEntity();
        containAppEntity.setResource(resourceEntity);
        containAppEntity.setApp(appBinaryeEntity);
        em.persist(containAppEntity);
        em.flush();
        
        return containAppEntity;
    }    
}
