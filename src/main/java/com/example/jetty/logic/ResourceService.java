package com.example.jetty.logic;

import com.example.jetty.MyConst;
import com.example.jetty.entity.AppBinaryEntity;
import com.example.jetty.entity.ContainAppEntity;
import com.example.jetty.entity.ResourceEntity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;

/**
 * Service Class for ResourceBinaryEntity.
 * @author atsushi.hondoh
 */
@Slf4j
@Service
@Transactional
@NoArgsConstructor
public class ResourceService implements Serializable {
    @Inject
    private EntityManager em;
    
    @Inject
    private MyConst myconst;
        
    public ResourceEntity create(Map<String, Object> inJSON) throws Exception {
        
        ResourceEntity resourceEntity = new ResourceEntity();
        Converter.jsonToBean(inJSON, resourceEntity);
        
        String version = new SimpleDateFormat("yyyyMMdd").format(new Date());
        
        TypedQuery<Integer> query = em.createNamedQuery("ResourceEntity.maxBranchNo", Integer.class);
        query.setParameter("name", resourceEntity.getName());
        query.setParameter("version", version);
        Integer branchNo = query.getSingleResult();
        
        resourceEntity.setVersion(version);
        resourceEntity.setBranchNo(null == branchNo ? 0 : branchNo + 1);
        
        em.persist(resourceEntity);
        em.flush();
        
        return resourceEntity;
    }

    public ResourceEntity findById(Long id) throws Exception {
        ResourceEntity resourceEntity = em.find(ResourceEntity.class, id);
        
        return resourceEntity;
    }
    
    public List<ResourceEntity> findByName(String name) throws Exception {
        TypedQuery<ResourceEntity> query = em.createNamedQuery("ResourceEntity.findByName", ResourceEntity.class);
        query.setParameter("name", name);
        List<ResourceEntity> resultList = query.getResultList();
                
        return resultList;
    }
    
    public List<ResourceEntity> findAll() throws Exception {
        TypedQuery<ResourceEntity> query = em.createNamedQuery("ResourceEntity.findAll", ResourceEntity.class);
        List<ResourceEntity> resultList = query.setMaxResults(1000).getResultList();
        
        return resultList;
    }

    public ResourceEntity update(Long id, Map<String, Object> inJSON) throws Exception {
        ResourceEntity resourceEntity = em.find(ResourceEntity.class, id);
        Converter.jsonToBean(inJSON, resourceEntity);
        em.merge(resourceEntity);
        
        return resourceEntity;
    }
    
    public ResourceEntity delete(Long id) {
        ResourceEntity resourceEntity = em.find(ResourceEntity.class, id);
        em.remove(resourceEntity);
        
        return resourceEntity;
    }

    public List<AppBinaryEntity> findContainsAppBinaryById(Long id) {
        ResourceEntity resourceEntity = em.find(ResourceEntity.class, id);
        if (null == resourceEntity) {
            return null;
        }
        
        List<AppBinaryEntity> appBinaryEntityArray = new ArrayList<>();
        for (ContainAppEntity joint : resourceEntity.getContains()){
            appBinaryEntityArray.add(joint.getApp());
        }
        return appBinaryEntityArray;
    }
}
