package com.smartfitai.models.dto;

import java.util.List;

public class WorkoutPlanResponse {
    
    private Long id;
    private String name;
    private String description;
    private String difficulty;
    private String focusAreas;
    private List<Exercise> exercises;
    
    public WorkoutPlanResponse() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public List<Exercise> getExercises() {
        return exercises;
    }
    
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
