package com.example.jetty.logic.test;

import com.example.jetty.entity.AppBinaryEntity;
import com.example.jetty.entity.ContainAppEntity;
import com.example.jetty.entity.ResourceEntity;
import com.example.jetty.logic.AppBinaryService;
import com.example.jetty.logic.ContainAppService;
import com.example.jetty.logic.ResourceService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import mockit.Deencapsulation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class for ContainAppServiceTest.
 *
 * @author atsushihondoh
 */
public class ContainAppServiceTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ContainAppService containAppService = new ContainAppService();
    private static AppBinaryService appBinService = new AppBinaryService();
    private static ResourceService resourceService = new ResourceService();
 
    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("JettyExamTestPU");
        em = emf.createEntityManager();
        // Injection
        Deencapsulation.setField(containAppService, "em", em);
        Deencapsulation.setField(appBinService, "em", em);
        Deencapsulation.setField(resourceService, "em", em);
    }
 
    @AfterClass
    public static void tearDownClass() {
        em.close();
        emf.close();
    }
    
    @Before
    public void setUp(){
        em.getTransaction().begin();
    }
    
    @After
    public void tearDown() {
        em.getTransaction().rollback();        
    }
    
    @Test
    public void test() throws Exception{
        // register App1
        Map<String, Object> appMap = new HashMap<>();
        appMap.put("name","myapp");
        appMap.put("enabled",true);
        AppBinaryEntity newAppBinary = appBinService.create(appMap);
        
        // register App2
        Map<String, Object> appMa2 = new HashMap<>();
        appMap.put("name","myapp2");
        appMap.put("enabled",true);
        appBinService.create(appMap);

        // register Resource
        Map<String, Object> resourceMap = new HashMap<>();
        resourceMap.put("name","myresource");
        resourceMap.put("directory", "/tmp/myresource");
        resourceMap.put("enabled",true);
        ResourceEntity newResource = resourceService.create(resourceMap);
        
        // Connect Resource and App1 (not App2)
        Map<String, Object> containAppMap = new HashMap<>();
        containAppMap.put("resource", newResource.getId());
        containAppMap.put("app", newAppBinary.getId());
        ContainAppEntity newContains = containAppService.create(containAppMap);
        em.getTransaction().commit();
        em.getTransaction().begin();
        // Get apps contained by the Resource.
//        System.out.println(resourceService.findById(newResource.getId()));
//        System.out.println(containAppService.findById(newContains.getId()));      
        List<AppBinaryEntity> containsApps = resourceService.findContainsAppBinaryById(newResource.getId());        
        assertThat(containsApps.size(),is(1));
        assertThat(containsApps.get(0).getName(),is("myapp"));
    }
}
