package com.smartfitai.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTest {

    @Test
    public void testExerciseGettersAndSetters() {
        Exercise exercise = new Exercise();
        exercise.setName("Push Up");
        exercise.setReps(20);
        exercise.setSets(3);

        assertEquals("Push Up", exercise.getName());
        assertEquals(20, exercise.getReps());
        assertEquals(3, exercise.getSets());
    }
}
