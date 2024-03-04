package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.GroupInfo;
import com.task.theeducationalinstitute.dto.RoutineRequest;
import com.task.theeducationalinstitute.dto.RoutineResponse;
import com.task.theeducationalinstitute.dto.TeacherInfo;
import com.task.theeducationalinstitute.entity.Group;
import com.task.theeducationalinstitute.entity.Routine;
import com.task.theeducationalinstitute.entity.Teacher;
import com.task.theeducationalinstitute.exception.GroupNotFoundException;
import com.task.theeducationalinstitute.exception.TeacherNotFoundException;
import com.task.theeducationalinstitute.repository.GroupRepository;
import com.task.theeducationalinstitute.repository.RoutineRepository;
import com.task.theeducationalinstitute.repository.TeacherRepository;
import com.task.theeducationalinstitute.utils.RoutineUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoutineServiceImplementation implements RoutineService{

    //we will inject the routineRepository dependency where required.
    @Autowired
    RoutineRepository routineRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    TeacherRepository teacherRepository;
    /*
        Creating a routine means saving the routine information in our database.
        Also, we will check if routine already exists.[Optionally, using a response code and message for now.]
        Can be replaced by throwing an exception as well.
        --> throw new RuntimeException("Routine already exists for the specified group and teacher.");
     */
    @Override
    public RoutineResponse createRoutine(RoutineRequest routineRequest) {
        // Check if the group exists
        if (!groupRepository.existsById(routineRequest.getGroupId())) {
            throw new GroupNotFoundException("Invalid GroupId: " + routineRequest.getGroupId());
        }

        // Check if the teacher exists
        if (!teacherRepository.existsById(routineRequest.getTeacherId())) {
            throw new TeacherNotFoundException("Invalid TeacherId: " + routineRequest.getTeacherId());
        }

            //newRoutine is created with following members.
            Routine newRoutine = Routine.builder()
                    .routineName(routineRequest.getRoutineName())
                    .routineDate(routineRequest.getRoutineDate())
                    .startTime(routineRequest.getStartTime())
                    .endTime(routineRequest.getEndTime())
                    .group(Group.builder()
                            .groupId(routineRequest.getGroupId())
                            .build())
                    .teacher(Teacher.builder()
                            .teacherId(routineRequest.getTeacherId())
                            .build())
                    .build();

            Routine savedRoutine = routineRepository.save(newRoutine);

            return RoutineResponse.builder()
                    .teacherId(savedRoutine.getTeacher().getTeacherId())
                    .groupId(savedRoutine.getGroup().getGroupId())
                    .responseCode(RoutineUtils.ROUTINE_CREATION_CODE)
                    .responseMessage(RoutineUtils.ROUTINE_CREATION_MESSAGE)
                    .build();
        }
    }