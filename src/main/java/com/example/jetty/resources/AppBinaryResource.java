package com.example.jetty.resources;

import com.example.jetty.entity.AppBinaryEntity;
import com.example.jetty.logic.AppBinaryService;
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
public class AppBinaryResource {

    @Inject
    private AppBinaryService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AppBinaryEntity create(Map<String, Object> inJSON) {
        try {
            log.info("Create Application Binary");
            return service.create(inJSON);
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    @GET
    @Path("/findById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AppBinaryEntity findById(@PathParam("id") Long id) {
        try {
            log.info("Find Application Binary");
            AppBinaryEntity entity = service.findById(id);
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
    @Path("/findByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AppBinaryEntity> findByName(@PathParam("name") String name) {
        try {
            log.info("Find Application Binary");
            return service.findByName(name);
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }
    
    @GET
    @Path("/names")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> names() {
        try {
            return service.names();
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }
    
    @GET
    @Path("/findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AppBinaryEntity> findAll() {
        try {
            log.info("Find Application Binary");
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
    public AppBinaryEntity update(@PathParam("id") Long id, Map<String, Object> inJSON) {
        try {
            log.info("Update Application Binary");
            return service.update(id, inJSON);
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AppBinaryEntity delete(@PathParam("id") Long id) {
        try {
            log.info("Delete Application Binary");
            return service.delete(id);
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }
}
