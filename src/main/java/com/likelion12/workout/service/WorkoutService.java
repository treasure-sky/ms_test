package com.likelion12.workout.service;

import com.likelion12.workout.dto.WorkoutInput;
import com.likelion12.workout.dto.WorkoutOutput;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.likelion12.workout.dto.WorkoutPartOutput;
import com.likelion12.workout.model.Workout;
import com.likelion12.workout.repository.WorkoutRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


/**
 * 임시로 만든 운동기록 서비스.
 */
@Service
public class WorkoutService {
    private static final List<Workout> workoutsRepository = new ArrayList<>();      // 임시 데이터베이스
    private final WorkoutRepository workoutRepository;
    public WorkoutService(WorkoutRepository workoutRepository){
        this.workoutRepository = workoutRepository;
    }

    /**
     * 운동기록 저장
     * @param workoutInput 저장할 데이터 정보
     * @return WorkoutOuput: 저장한 workout 기록을 출력
     */
    public WorkoutOutput create(WorkoutInput workoutInput){
        // workoutInput(유저에게 받은 데이터 객체) -> workout[newData](데이터베이스에 저장될 객체)
        Workout newData = new Workout();
        newData.date = LocalDateTime.now();
        newData.exerciseName = workoutInput.getExerciseName();
        newData.duration = workoutInput.getDuration();
        newData.content = workoutInput.getContent();

        // newData 저장 및 저장된 객체 리턴
        workoutRepository.save(newData);
        return newData.toWorkoutOutput();
    }

    /**
     * 저장되어 있는 모든 운동기록을 리턴
     * @return WorkoutOutput List
     */
    public List<WorkoutOutput> findAll(){
        // 데이터베이스의 모든 정보를 읽고 유저에게 리턴할 데이터(workoutOutput)로 맞춰 변환해 리턴
        List<WorkoutOutput> result = new ArrayList<>();
        for (Workout workout : workoutRepository.findAll()) {
            result.add(workout.toWorkoutOutput());
        }
        return result;
    }
    /**
     * 페이지 단위로 운동기록을 리턴
     *
     * @param howMany 한 페이지에 들어가는 운동기록의 수
     * @param pageNum 페이지번호
     * @return WorkoutOutput List
     */
    public WorkoutPartOutput findPart(Integer howMany, Integer pageNum){
        List<WorkoutOutput> result = new ArrayList<>();
        if(howMany==0){
            return new WorkoutPartOutput(result, 0);
        }
        Pageable pageable = PageRequest.of(pageNum, howMany);
        for(Workout workout: workoutRepository.findAll(pageable).getContent()){
            result.add(workout.toWorkoutOutput());
        }
        return new WorkoutPartOutput(
                result,
                (int) workoutRepository.count()/howMany + (workoutRepository.count()%howMany==0 ? 0:1)
        );
    }

    /**
     * 운동기록 수정
     * @param id 수정될 운동기록의 id
     * @param workoutInput 수정에 적용할 내용
     * @return 수정된 운동기록
     */
    public WorkoutOutput update(Long id, WorkoutInput workoutInput){
        // id에 해당하는 운동기록 조회
        Workout data = workoutRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id+" not found"));

        // 수정될 내용 적용 및 리턴
        if(workoutInput.getExerciseName()!=null) data.exerciseName = workoutInput.getExerciseName();
        if(workoutInput.getDuration()!=null) data.duration = workoutInput.getDuration();
        if(workoutInput.getContent()!=null) data.content = workoutInput.getContent();
        workoutRepository.save(data);
        return data.toWorkoutOutput();
    }

    /**
     * 운동기록 삭제
     * @param id 삭제될 운동기록의 id
     */
    public void delete(Long id){
        // id에 해당하는 운동기록 조회
        Workout data = workoutRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id+" not found"));

        // 삭제
        workoutRepository.delete(data);
    }
}
