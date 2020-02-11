package com.example.jetty.logic;

import com.example.jetty.MyConst;
import com.example.jetty.entity.AppBinaryEntity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;

/**
 * Service Class for AppBinaryEntity.
 * @author atsushi.hondoh
 */
@Slf4j
@Service
@Transactional
@NoArgsConstructor
public class AppBinaryService implements Serializable {
    @Inject
    private EntityManager em;
    
    @Inject
    private MyConst myconst;
        
    public AppBinaryEntity create(Map<String, Object> inJSON) throws Exception {
        
        AppBinaryEntity appBinaryEntity = new AppBinaryEntity();
        Converter.jsonToBean(inJSON, appBinaryEntity);
        
        String version = new SimpleDateFormat("yyyyMMdd").format(new Date());
        
        Query query = em.createNamedQuery("AppBinaryEntity.maxBranchNo");
        query.setParameter("name", appBinaryEntity.getName());
        query.setParameter("version", version);
        Integer branchNo = (Integer) query.getSingleResult();
        
        appBinaryEntity.setVersion(version);
        appBinaryEntity.setBranchNo(null == branchNo ? 0 : branchNo + 1);
        
        em.persist(appBinaryEntity);
        em.flush();
        
        return appBinaryEntity;
    }

    public AppBinaryEntity findById(Long id) throws Exception {
        AppBinaryEntity appBinaryEntity = em.find(AppBinaryEntity.class, id);
        
        return appBinaryEntity;
    }
    
    public List<AppBinaryEntity> findByName(String name) throws Exception {
        Query query = em.createNamedQuery("AppBinaryEntity.findByName");
        query.setParameter("name", name);
        List<AppBinaryEntity> resultList = query.getResultList();
                
        return resultList;
    }
    
    public List<AppBinaryEntity> findAll() throws Exception {
        Query query = em.createNamedQuery("AppBinaryEntity.findAll");
        List<AppBinaryEntity> resultList = query.setMaxResults(1000).getResultList();
        
        return resultList;
    }

    public AppBinaryEntity update(Long id, Map<String, Object> inJSON) throws Exception {
        AppBinaryEntity appBinaryEntity = em.find(AppBinaryEntity.class, id);
        Converter.jsonToBean(inJSON, appBinaryEntity);
        em.merge(appBinaryEntity);
        
        return appBinaryEntity;
    }
    
    public AppBinaryEntity delete(Long id) {
        AppBinaryEntity appBinaryEntity = em.find(AppBinaryEntity.class, id);
        em.remove(appBinaryEntity);
        
        return appBinaryEntity;
    }
}