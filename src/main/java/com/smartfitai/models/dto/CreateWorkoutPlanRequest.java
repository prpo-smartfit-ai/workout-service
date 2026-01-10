package com.smartfitai.models.dto;

import java.util.List;

public class CreateWorkoutPlanRequest {
    
    private String name;
    private String description;
    private String difficulty;
    private String focusAreas;
    private List<WorkoutPlanExerciseRequest> exercises;
    
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

    public List<WorkoutPlanExerciseRequest> getExercises() {
        return exercises;
    }

    public void setExercises(List<WorkoutPlanExerciseRequest> exercises) {
        this.exercises = exercises;
    }

    public static class WorkoutPlanExerciseRequest {
        private String name;
        private Integer sets;
        private Integer reps;
        private Integer duration;
        private String instructions;
        private String difficulty;

        public WorkoutPlanExerciseRequest() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getSets() { return sets; }
        public void setSets(Integer sets) { this.sets = sets; }
        public Integer getReps() { return reps; }
        public void setReps(Integer reps) { this.reps = reps; }
        public Integer getDuration() { return duration; }
        public void setDuration(Integer duration) { this.duration = duration; }
        public String getInstructions() { return instructions; }
        public void setInstructions(String instructions) { this.instructions = instructions; }
        public String getDifficulty() { return difficulty; }
        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    }
}
