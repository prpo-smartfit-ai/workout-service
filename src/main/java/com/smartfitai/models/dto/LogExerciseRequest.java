package com.smartfitai.models.dto;

public class LogExerciseRequest {
    
    private Long exerciseId;
    private Integer setsCompleted;
    private Integer repsCompleted;
    private String notes;
    
    public LogExerciseRequest() {}
    
    public Long getExerciseId() {
        return exerciseId;
    }
    
    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }
    
    public Integer getSetsCompleted() {
        return setsCompleted;
    }
    
    public void setSetsCompleted(Integer setsCompleted) {
        this.setsCompleted = setsCompleted;
    }
    
    public Integer getRepsCompleted() {
        return repsCompleted;
    }
    
    public void setRepsCompleted(Integer repsCompleted) {
        this.repsCompleted = repsCompleted;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
