package com.smartfitai.api.v1.resources;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@RequestScoped
@Path("/workouts")
@Produces(MediaType.APPLICATION_JSON)
public class WorkoutResource {

    @GET
    @Path("/health")
    public Response healthCheck() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "workout-service");
        return Response.ok(health).build();
    }

    @GET
    public Response getAllWorkouts() {
        Map<String, String> message = new HashMap<>();
        message.put("message", "Workout service is running!");
        message.put("version", "1.0.0");
        return Response.ok(message).build();
    }
}
