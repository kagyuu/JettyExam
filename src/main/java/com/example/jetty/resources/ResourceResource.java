package com.example.jetty.resources;

import com.example.jetty.entity.AppBinaryEntity;
import com.example.jetty.entity.ResourceEntity;
import com.example.jetty.logic.ResouceService;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/app")
public class ResourceResource {

    @Inject
    private ResouceService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResourceEntity create(Map<String, Object> inJSON) {
        try {
            log.info("Create Resource");
            return service.create(inJSON);
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    @GET
    @Path("/findById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResourceEntity findById(@PathParam("id") Long id) {
        try {
            log.info("Find Resource");
            ResourceEntity entity = service.findById(id);
            if (null == entity) {
                throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
            }
            return entity;
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }
    
    @GET
    @Path("/findContainsAppBinary/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AppBinaryEntity> findContainsAppBinaryById(@PathParam("id") Long id) {
        try {
            log.info("Find Resource");
            List<AppBinaryEntity> appBinaryArray = service.findContainsAppBinaryById(id);
            if (null == appBinaryArray) {
                throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
            }
            return appBinaryArray;
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }
    
    @GET
    @Path("/findByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ResourceEntity> findByName(@PathParam("name") String name) {
        try {
            log.info("Find Resource");
            return service.findByName(name);
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }
    
    @GET
    @Path("/findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ResourceEntity> findAll() {
        try {
            log.info("Find Resource");
            return service.findAll();
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResourceEntity update(@PathParam("id") Long id, Map<String, Object> inJSON) {
        try {
            log.info("Update Resource");
            return service.update(id, inJSON);
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResourceEntity delete(@PathParam("id") Long id) {
        try {
            log.info("Delete Resource");
            return service.delete(id);
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }
}
