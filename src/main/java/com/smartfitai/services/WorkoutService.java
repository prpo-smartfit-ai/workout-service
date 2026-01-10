package com.smartfitai.services;

import com.smartfitai.models.Exercise;
import com.smartfitai.models.SessionExercise;
import com.smartfitai.models.WorkoutPlan;
import com.smartfitai.models.WorkoutSession;
import com.smartfitai.models.dto.CreateWorkoutPlanRequest;
import com.smartfitai.models.dto.SessionExerciseResponse;
import com.smartfitai.models.dto.SessionResponse;
import com.smartfitai.models.dto.StartSessionRequest;
import com.smartfitai.models.dto.WorkoutPlanResponse;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class WorkoutService {
    
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public WorkoutPlanResponse createWorkoutPlan(Long userId, CreateWorkoutPlanRequest request) {
        WorkoutPlan plan = new WorkoutPlan();
        plan.setUserId(userId);
        plan.setName(request.getName());
        plan.setDescription(request.getDescription());
        plan.setDifficulty(request.getDifficulty());
        plan.setFocusAreas(request.getFocusAreas());
        
        List<Exercise> exercises = new ArrayList<>();
        if (request.getExercises() != null) {
            for (CreateWorkoutPlanRequest.WorkoutPlanExerciseRequest exerciseReq : request.getExercises()) {
                Exercise exercise = new Exercise();
                exercise.setName(exerciseReq.getName());
                exercise.setSets(exerciseReq.getSets() != null ? exerciseReq.getSets() : 0);
                exercise.setReps(exerciseReq.getReps() != null ? exerciseReq.getReps() : 0);
                exercise.setDuration(exerciseReq.getDuration());
                exercise.setInstructions(exerciseReq.getInstructions());
                exercise.setDifficulty(exerciseReq.getDifficulty() != null ? exerciseReq.getDifficulty() : request.getDifficulty());
                exercise.setWorkoutPlan(plan);
                exercises.add(exercise);
            }
        }
        plan.setExercises(exercises);
        
        em.persist(plan);
        em.flush();
        
        return mapWorkoutPlanToResponse(plan);
    }

    public List<WorkoutPlanResponse> getWorkoutPlans(Long userId) {
        try {
            List<WorkoutPlan> plans = em.createQuery(
                    "SELECT p FROM WorkoutPlan p WHERE p.userId = :userId ORDER BY p.createdDate DESC",
                    WorkoutPlan.class)
                    .setParameter("userId", userId)
                    .getResultList();
            
            return plans.stream()
                    .map(this::mapWorkoutPlanToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public WorkoutPlanResponse getWorkoutPlanById(Long planId) {
        try {
            WorkoutPlan plan = em.find(WorkoutPlan.class, planId);
            if (plan != null) {
                return mapWorkoutPlanToResponse(plan);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public boolean deleteWorkoutPlan(Long planId, Long userId) {
        try {
            WorkoutPlan plan = em.find(WorkoutPlan.class, planId);
            if (plan != null && plan.getUserId().equals(userId)) {
                em.remove(plan);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public SessionResponse startSession(Long userId, StartSessionRequest request) {
        WorkoutPlan plan = em.find(WorkoutPlan.class, request.getWorkoutPlanId());
        
        if (plan == null) {
            throw new IllegalArgumentException("Workout plan not found");
        }
        
        WorkoutSession session = new WorkoutSession();
        session.setUserId(userId);
        session.setWorkoutPlanId(plan.getId());
        session.setStartDate(LocalDateTime.now());
        
        em.persist(session);
        em.flush();
        
        return mapWorkoutSessionToResponse(session);
    }

    @Transactional
    public void logExerciseCompletion(Long sessionId, Long exerciseId, Integer setsCompleted, Integer repsCompleted, String notes) {
        WorkoutSession session = em.find(WorkoutSession.class, sessionId);
        
        if (session == null) {
            throw new IllegalArgumentException("Workout session not found");
        }
        
        Exercise exercise = em.find(Exercise.class, exerciseId);
        
        if (exercise == null) {
            throw new IllegalArgumentException("Exercise not found");
        }
        
        SessionExercise sessionExercise = new SessionExercise();
        sessionExercise.setSession(session);
        sessionExercise.setExerciseId(exerciseId);
        sessionExercise.setSetsCompleted(setsCompleted);
        sessionExercise.setRepsCompleted(repsCompleted);
        sessionExercise.setNotes(notes);
        sessionExercise.setCompleted(true);
        
        em.persist(sessionExercise);
        em.flush();
    }

    @Transactional
    public SessionResponse endSession(Long sessionId, String notes) {
        WorkoutSession session = em.find(WorkoutSession.class, sessionId);
        
        if (session == null) {
            throw new IllegalArgumentException("Workout session not found");
        }
        
        session.setEndDate(LocalDateTime.now());
        session.setNotes(notes);
        
        em.merge(session);
        em.flush();
        
        return mapWorkoutSessionToResponse(session);
    }

    public List<SessionResponse> getSessionHistory(Long userId) {
        try {
            List<WorkoutSession> sessions = em.createQuery(
                    "SELECT s FROM WorkoutSession s WHERE s.userId = :userId ORDER BY s.createdDate DESC",
                    WorkoutSession.class)
                    .setParameter("userId", userId)
                    .getResultList();
            
            return sessions.stream()
                    .map(this::mapWorkoutSessionToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public SessionResponse getSessionById(Long sessionId) {
        try {
            WorkoutSession session = em.find(WorkoutSession.class, sessionId);
            if (session != null) {
                return mapWorkoutSessionToResponse(session);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // Mapping helper methods
    private WorkoutPlanResponse mapWorkoutPlanToResponse(WorkoutPlan plan) {
        WorkoutPlanResponse response = new WorkoutPlanResponse();
        response.setId(plan.getId());
        response.setName(plan.getName());
        response.setDescription(plan.getDescription());
        response.setDifficulty(plan.getDifficulty());
        response.setFocusAreas(plan.getFocusAreas());
        
        if (plan.getExercises() != null) {
            response.setExercises(
                    plan.getExercises().stream()
                            .map(this::mapExerciseToDto)
                            .collect(Collectors.toList())
            );
        }
        
        return response;
    }

    private SessionResponse mapWorkoutSessionToResponse(WorkoutSession session) {
        SessionResponse response = new SessionResponse();
        response.setId(session.getId());
        response.setWorkoutPlanId(session.getWorkoutPlanId());
        response.setStartDate(session.getStartDate());
        response.setEndDate(session.getEndDate());
        response.setNotes(session.getNotes());
        
        if (session.getSessionExercises() != null) {
            response.setSessionExercises(
                    session.getSessionExercises().stream()
                            .map(this::mapSessionExerciseToResponse)
                            .collect(Collectors.toList())
            );
            
            // Calculate exercises completed
            long completedCount = session.getSessionExercises().stream()
                    .filter(se -> se.getCompleted() != null && se.getCompleted())
                    .count();
            response.setExercisesCompleted((int) completedCount);
        } else {
            response.setExercisesCompleted(0);
        }

        // Try to get total exercises from the plan
        if (session.getWorkoutPlanId() != null) {
            WorkoutPlan plan = em.find(WorkoutPlan.class, session.getWorkoutPlanId());
            if (plan != null && plan.getExercises() != null) {
                response.setTotalExercises(plan.getExercises().size());
            }
        }
        
        return response;
    }

    private com.smartfitai.models.dto.Exercise mapExerciseToDto(Exercise exercise) {
        com.smartfitai.models.dto.Exercise dto = new com.smartfitai.models.dto.Exercise();
        dto.setId(exercise.getId());
        dto.setName(exercise.getName());
        dto.setSets(exercise.getSets());
        dto.setReps(exercise.getReps());
        dto.setDuration(exercise.getDuration());
        dto.setInstructions(exercise.getInstructions());
        dto.setEquipment(exercise.getEquipment());
        dto.setDifficulty(exercise.getDifficulty());
        return dto;
    }

    private SessionExerciseResponse mapSessionExerciseToResponse(SessionExercise sessionExercise) {
        SessionExerciseResponse response = new SessionExerciseResponse();
        response.setId(sessionExercise.getId());
        response.setExerciseId(sessionExercise.getExerciseId());
        response.setSetsCompleted(sessionExercise.getSetsCompleted());
        response.setRepsCompleted(sessionExercise.getRepsCompleted());
        response.setNotes(sessionExercise.getNotes());
        response.setCompleted(sessionExercise.getCompleted());
        return response;
    }
}
