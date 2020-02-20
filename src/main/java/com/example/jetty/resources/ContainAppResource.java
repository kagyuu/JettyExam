package com.example.jetty.resources;

import com.example.jetty.entity.ContainAppEntity;
import com.example.jetty.logic.ContainAppService;
import java.net.HttpURLConnection;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/app")
public class ContainAppResource {

    @Inject
    private ContainAppService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ContainAppEntity create(@PathParam("resource") Long resourceId, @PathParam("app") Long appId) {
        try {
            log.info("Connect Resource and Application Binary");
            return service.create(resourceId, appId);
        } catch (Exception ex) {
            log.error("ERROR", ex);
            throw new WebApplicationException(ex, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }
}
