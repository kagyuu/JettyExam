package com.example.jetty.resources;

import com.example.jetty.session.HelloSession;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/hello")
public class HelloResource {

    @Inject
    private HelloSession session;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HelloBean create(HelloBean newEmployee) {
        log.info("Create new Employee");
        return newEmployee;
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HelloBean> read(@PathParam("id") String id) {
        log.info("Get Employee {}", id);
        List<HelloBean> resultList = new ArrayList<>();
        resultList.add(new HelloBean(id));
        return resultList;
    }
    
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public HelloBean update(@PathParam("id") String id, HelloBean newEmployee) {
        log.info("Update Employee {}", id);
        return new HelloBean(id);
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HelloBean delete(@PathParam("id") String id) {
        log.info("Delete Employee {}", id);
        return new HelloBean(id);
    }
}
