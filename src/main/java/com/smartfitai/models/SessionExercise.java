package com.smartfitai.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_exercises")
public class SessionExercise {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private WorkoutSession session;
    
    @Column(name = "exercise_id", nullable = false)
    private Long exerciseId;
    
    @Column(name = "sets_completed")
    private Integer setsCompleted;
    
    @Column(name = "reps_completed")
    private Integer repsCompleted;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(nullable = false)
    private Boolean completed;
    
    @Column(nullable = false)
    private LocalDateTime createdDate;
    
    public SessionExercise() {
        this.completed = false;
        this.createdDate = LocalDateTime.now();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public WorkoutSession getSession() {
        return session;
    }
    
    public void setSession(WorkoutSession session) {
        this.session = session;
    }
    
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
    
    public Boolean getCompleted() {
        return completed;
    }
    
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
