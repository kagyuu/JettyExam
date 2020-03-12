package com.example.jetty.logic.test;

import com.example.jetty.entity.ResourceEntity;
import com.example.jetty.logic.ResourceService;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class for ResourceService.
 *
 * @author atsushihondoh
 */
public class ResourceServiceTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ResourceService target = new ResourceService();
 
    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("JettyExamTestPU");
        em = emf.createEntityManager();
        // Injection
        Deencapsulation.setField(target, "em", em);
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
    public void testCreate1() throws Exception {
        String expectVersion = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
        
        Map<String, Object> arg = new HashMap<>();
        arg.put("name","myapp");
        ResourceEntity newEntity = target.create(arg);
        assertThat(newEntity.getName(),is("myapp"));
        assertThat(newEntity.getVersion(),is(expectVersion));
        assertThat(newEntity.getBranchNo(), is(0));
        assertFalse(newEntity.isEnabled());
    }

    @Test
    public void testCreate2() throws Exception {
        String expectVersion = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
        
        Map<String, Object> arg = new HashMap<>();
        arg.put("name","yourapp");
        arg.put("directory", "product");
        ResourceEntity newEntity = target.create(arg);
        assertThat(newEntity.getName(),is("yourapp"));
        assertThat(newEntity.getVersion(),is(expectVersion));
        assertThat(newEntity.getBranchNo(), is(0));
        assertFalse(newEntity.isEnabled());
        
        ResourceEntity newEntity2 = target.create(arg);
        assertThat(newEntity2.getName(),is("yourapp"));
        assertThat(newEntity2.getVersion(),is(expectVersion));
        assertThat(newEntity2.getBranchNo(), is(1));
        assertFalse(newEntity2.isEnabled());
    }
    
    @Test
    public void testFindAndUpdate() throws Exception {
        String expectVersion = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
        
        // Create New Entity
        Map<String, Object> arg1 = new HashMap<>();
        arg1.put("name","myapp");
        arg1.put("directory", "dev");
        target.create(arg1);
        target.create(arg1);
        ResourceEntity newEntity = target.create(arg1);
        assertThat(newEntity.getName(),is("myapp"));
        assertThat(newEntity.getVersion(),is(expectVersion));
        assertThat(newEntity.getBranchNo(), is(2));
        assertFalse(newEntity.isEnabled());
        
        // Make enable
        Long id = newEntity.getId();
        Map<String, Object> arg2 = new HashMap<>();
        arg2.put("enabled",true);
        target.update(id, arg2);
        
        // Fine Enabled Entity
        List<ResourceEntity> searchResult = target.findByName("dev", "myapp");
        assertThat(searchResult.size(),is(1));
        ResourceEntity searchedEntity = searchResult.get(0);
        assertThat(searchedEntity.getName(),is("myapp"));
        assertThat(searchedEntity.getVersion(),is(expectVersion));
        assertThat(searchedEntity.getBranchNo(), is(2));
        assertTrue(searchedEntity.isEnabled());
    }
}