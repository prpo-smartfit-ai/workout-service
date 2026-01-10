package com.smartfitai.models.dto;

public class StartSessionRequest {
    
    private Long workoutPlanId;
    
    public StartSessionRequest() {}
    
    public Long getWorkoutPlanId() {
        return workoutPlanId;
    }
    
    public void setWorkoutPlanId(Long workoutPlanId) {
        this.workoutPlanId = workoutPlanId;
    }
}
