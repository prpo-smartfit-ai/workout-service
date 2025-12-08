package com.smartfitai.models.dto;

public class CreateWorkoutPlanRequest {
    
    private String name;
    private String description;
    private String difficulty;
    private String focusAreas;
    
    public CreateWorkoutPlanRequest() {}
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public String getFocusAreas() {
        return focusAreas;
    }
    
    public void setFocusAreas(String focusAreas) {
        this.focusAreas = focusAreas;
    }
}
