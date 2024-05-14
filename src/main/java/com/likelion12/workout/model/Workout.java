package com.likelion12.workout.model;

import com.likelion12.workout.dto.WorkoutOutput;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

/**
 * 데이터베이스에 저장될 객체
 */
@Entity
public class Workout{
    @Id  // Id 지정 어노테이션 Entity의 경우 꼭 있어야 함
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // MySQL에게 키생성을 맡김(값 지정을 직접할 필요가 없어진다)
    public Long id;

    public String exerciseName;
    public String duration;
    public LocalDateTime date;
    public String content;

    public WorkoutOutput toWorkoutOutput(){
        return new WorkoutOutput(this.id, this.exerciseName, this.duration, this.date, this.content);
    }
}
