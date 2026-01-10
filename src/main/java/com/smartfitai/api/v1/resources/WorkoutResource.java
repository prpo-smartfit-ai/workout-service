package com.smartfitai.api.v1.resources;

import com.smartfitai.config.Secured;
import com.smartfitai.models.dto.CreateWorkoutPlanRequest;
import com.smartfitai.models.dto.LogExerciseRequest;
import com.smartfitai.models.dto.SessionResponse;
import com.smartfitai.models.dto.StartSessionRequest;
import com.smartfitai.models.dto.WorkoutPlanResponse;
import com.smartfitai.services.WorkoutService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScoped
@Path("/workouts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkoutResource {

    @Inject
    private WorkoutService workoutService;
    
    @Context
    private ContainerRequestContext requestContext;

    @GET
    @Path("/health")
    public Response healthCheck() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "workout-service");
        return Response.ok(health).build();
    }

    @POST
    @Path("/plans")
    @Secured
    public Response createWorkoutPlan(CreateWorkoutPlanRequest request) {
        try {
            Long userId = (Long) requestContext.getProperty("userId");
            
            if (request.getName() == null || request.getName().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ApiResponse<>(null, "Workout plan name is required"))
                        .build();
            }
            
            WorkoutPlanResponse plan = workoutService.createWorkoutPlan(userId, request);
            return Response.status(Response.Status.CREATED)
                    .entity(new ApiResponse<>(plan, "Workout plan created successfully"))
                    .build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to create workout plan: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/plans")
    @Secured
    public Response getWorkoutPlans() {
        try {
            Long userId = (Long) requestContext.getProperty("userId");
            List<WorkoutPlanResponse> plans = workoutService.getWorkoutPlans(userId);
            return Response.ok(new ApiResponse<>(plans, "Workout plans retrieved successfully")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to retrieve workout plans: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/plans/{planId}")
    @Secured
    public Response getWorkoutPlan(@PathParam("planId") Long planId) {
        try {
            WorkoutPlanResponse plan = workoutService.getWorkoutPlanById(planId);
            
            if (plan == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiResponse<>(null, "Workout plan not found"))
                        .build();
            }
            
            return Response.ok(new ApiResponse<>(plan, "Workout plan retrieved successfully")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to retrieve workout plan: " + e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/plans/{planId}")
    @Secured
    public Response deleteWorkoutPlan(@PathParam("planId") Long planId) {
        try {
            Long userId = (Long) requestContext.getProperty("userId");
            boolean deleted = workoutService.deleteWorkoutPlan(planId, userId);
            
            if (!deleted) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiResponse<>(null, "Workout plan not found or you don't have permission to delete it"))
                        .build();
            }
            
            return Response.ok(new ApiResponse<>(null, "Workout plan deleted successfully")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to delete workout plan: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/sessions")
    @Secured
    public Response startSession(StartSessionRequest request) {
        try {
            Long userId = (Long) requestContext.getProperty("userId");
            
            if (request.getWorkoutPlanId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ApiResponse<>(null, "Workout plan ID is required"))
                        .build();
            }
            
            SessionResponse session = workoutService.startSession(userId, request);
            return Response.status(Response.Status.CREATED)
                    .entity(new ApiResponse<>(session, "Workout session started successfully"))
                    .build();
            
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse<>(null, e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to start workout session: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/sessions")
    @Secured
    public Response getSessionHistory() {
        try {
            Long userId = (Long) requestContext.getProperty("userId");
            List<SessionResponse> sessions = workoutService.getSessionHistory(userId);
            return Response.ok(new ApiResponse<>(sessions, "Session history retrieved successfully")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to retrieve session history: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/sessions/user/{userId}")
    @Secured
    public Response getSessionHistory(@PathParam("userId") Long userId) {
        try {
            List<SessionResponse> sessions = workoutService.getSessionHistory(userId);
            return Response.ok(new ApiResponse<>(sessions, "Session history retrieved successfully")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to retrieve session history: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/sessions/{sessionId}")
    @Secured
    public Response getSession(@PathParam("sessionId") Long sessionId) {
        try {
            SessionResponse session = workoutService.getSessionById(sessionId);
            
            if (session == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiResponse<>(null, "Workout session not found"))
                        .build();
            }
            
            return Response.ok(new ApiResponse<>(session, "Workout session retrieved successfully")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to retrieve workout session: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/sessions/{sessionId}/exercises")
    @Secured
    public Response logExerciseCompletion(@PathParam("sessionId") Long sessionId, LogExerciseRequest request) {
        try {
            if (request.getExerciseId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ApiResponse<>(null, "Exercise ID is required"))
                        .build();
            }
            
            workoutService.logExerciseCompletion(
                    sessionId,
                    request.getExerciseId(),
                    request.getSetsCompleted(),
                    request.getRepsCompleted(),
                    request.getNotes()
            );
            
            return Response.status(Response.Status.CREATED)
                    .entity(new ApiResponse<>(null, "Exercise completion logged successfully"))
                    .build();
            
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse<>(null, e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to log exercise completion: " + e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/sessions/{sessionId}/end")
    @Secured
    public Response endSession(@PathParam("sessionId") Long sessionId, EndSessionRequest request) {
        try {
            SessionResponse session = workoutService.endSession(sessionId, request.getNotes());
            
            return Response.ok(new ApiResponse<>(session, "Workout session ended successfully")).build();
            
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse<>(null, e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to end workout session: " + e.getMessage()))
                    .build();
        }
    }

    // DTO for ending a session
    public static class EndSessionRequest {
        private String notes;
        
        public EndSessionRequest() {}
        
        public String getNotes() {
            return notes;
        }
        
        public void setNotes(String notes) {
            this.notes = notes;
        }
    }

    // Generic API response wrapper
    public static class ApiResponse<T> {
        private T data;
        private String message;
        
        public ApiResponse() {}
        
        public ApiResponse(T data, String message) {
            this.data = data;
            this.message = message;
        }
        
        public T getData() {
            return data;
        }
        
        public void setData(T data) {
            this.data = data;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
}
