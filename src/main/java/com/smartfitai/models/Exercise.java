package com.smartfitai.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exercises")
public class Exercise {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id", nullable = false)
    private WorkoutPlan workoutPlan;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(nullable = false)
    private Integer sets;
    
    @Column(nullable = false)
    private Integer reps;
    
    @Column(name = "duration_seconds")
    private Integer duration;
    
    @Column(columnDefinition = "TEXT")
    private String instructions;
    
    @Column(length = 255)
    private String equipment;
    
    @Column(nullable = false, length = 50)
    private String difficulty;
    
    @Column(nullable = false)
    private LocalDateTime createdDate;
    
    public Exercise() {
        this.createdDate = LocalDateTime.now();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public WorkoutPlan getWorkoutPlan() {
        return workoutPlan;
    }
    
    public void setWorkoutPlan(WorkoutPlan workoutPlan) {
        this.workoutPlan = workoutPlan;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getSets() {
        return sets;
    }
    
    public void setSets(Integer sets) {
        this.sets = sets;
    }
    
    public Integer getReps() {
        return reps;
    }
    
    public void setReps(Integer reps) {
        this.reps = reps;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public String getInstructions() {
        return instructions;
    }
    
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    public String getEquipment() {
        return equipment;
    }
    
    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
