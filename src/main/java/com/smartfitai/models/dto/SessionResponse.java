package com.smartfitai.models.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SessionResponse {
    
    private Long id;
    private Long workoutPlanId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String notes;
    private List<SessionExerciseResponse> sessionExercises;
    
    public SessionResponse() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getWorkoutPlanId() {
        return workoutPlanId;
    }
    
    public void setWorkoutPlanId(Long workoutPlanId) {
        this.workoutPlanId = workoutPlanId;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public List<SessionExerciseResponse> getSessionExercises() {
        return sessionExercises;
    }
    
    public void setSessionExercises(List<SessionExerciseResponse> sessionExercises) {
        this.sessionExercises = sessionExercises;
    }
}
