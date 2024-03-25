package com.example.EducationalREST.service;

import com.example.EducationalREST.Exceptions.GroupNotValidException;
import com.example.EducationalREST.Exceptions.RoutineNotValidException;
import com.example.EducationalREST.Exceptions.TeacherNotValidException;
import com.example.EducationalREST.dto.RoutineCreationRequest;
import com.example.EducationalREST.entity.Group;
import com.example.EducationalREST.entity.Routine;
import com.example.EducationalREST.entity.Teacher;
import com.example.EducationalREST.repository.GroupRepository;
import com.example.EducationalREST.repository.RoutineRepository;
import com.example.EducationalREST.repository.TeacherRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoutineServiceImplementation implements RoutineService {

   @Autowired
   private RoutineRepository routineRepository;

   @Autowired
   private GroupRepository groupRepository;

   @Autowired
   private TeacherRepository teacherRepository;

    @Override
    public ResponseEntity addRoutine(RoutineCreationRequest routineCreationRequest) {
        // Check if the group exists
        if (!groupRepository.existsById(routineCreationRequest.getGroupId())) {
            throw new GroupNotValidException("Invalid GroupId: " + routineCreationRequest.getGroupId());
        }

        // Check if the teacher exists
        if (!teacherRepository.existsById(routineCreationRequest.getTeacherId())) {
            throw new TeacherNotValidException("Invalid TeacherId: " + routineCreationRequest.getTeacherId());
        }

        validateRoutine(routineCreationRequest);

        Routine newRoutine = Routine.builder()
                        .routineName(routineCreationRequest.getRoutineName())
                        .routineDate(routineCreationRequest.getRoutineDate())
                        .startTime(routineCreationRequest.getStartTime())
                        .endTime(routineCreationRequest.getEndTime())
                        .group(Group.builder()
                                .groupId(routineCreationRequest.getGroupId())
                                .build())
                         .teacher(Teacher.builder()
                                .teacherId(routineCreationRequest.getTeacherId())
                                .build())
                        .build();

        Routine savedRoutine =routineRepository.save(newRoutine);
        return ResponseEntity.ok().build();
    }


    //Validating the information of group entity to ensure none of them are left blank/empty.
    public void validateRoutine(RoutineCreationRequest routineCreationRequest)
    {
        if(StringUtils.isBlank(routineCreationRequest.getRoutineName()))
        {
            throw new RoutineNotValidException("Routine name cannot be empty");
        }

        if(routineCreationRequest.getRoutineDate()==null)
        {
            throw new RoutineNotValidException("Routine date cannot be null");
        }

        if(routineCreationRequest.getStartTime() == null || routineCreationRequest.getEndTime() == null || routineCreationRequest.getStartTime().isAfter(routineCreationRequest.getEndTime()))
        {
            throw new RoutineNotValidException("Start time must be before end time");
        }

    }
}
