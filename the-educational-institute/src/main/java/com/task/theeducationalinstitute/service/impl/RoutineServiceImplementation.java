package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.GroupInfo;
import com.task.theeducationalinstitute.dto.RoutineRequest;
import com.task.theeducationalinstitute.dto.RoutineResponse;
import com.task.theeducationalinstitute.dto.TeacherInfo;
import com.task.theeducationalinstitute.entity.Routine;
import com.task.theeducationalinstitute.repository.GroupRepository;
import com.task.theeducationalinstitute.repository.RoutineRepository;
import com.task.theeducationalinstitute.utils.RoutineUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class RoutineServiceImplementation implements RoutineService{

    //we will inject the routineRepository dependency where required.
    @Autowired
    RoutineRepository routineRepository;

    @Autowired
    GroupRepository groupRepository;

    /*
        Creating a routine means saving the routine information in our database.
        Also, we will check if routine already exists.[Optionally, using a response code and message for now.]
        Can be replaced by throwing an exception as well.
        --> throw new RuntimeException("Routine already exists for the specified group and teacher.");
     */
    @Override
    public RoutineResponse createRoutine(RoutineRequest routineRequest) {
        if(routineRepository.existsByGroupIdTeacherId(routineRequest.getGroupId(), routineRequest.getTeacherId()))
        {
            return RoutineResponse.builder()
                    .responseCode(RoutineUtils.ROUTINE_EXISTS_CODE)
                    .responseMessage(RoutineUtils.ROUTINE_EXISTS_MESSAGE)
                    .groupInfo(GroupInfo.builder()
                            .specialization(routineRequest.getGroupInfo().getSpecialization())
                            .gradeLevel(routineRequest.getGroupInfo().getGradeLevel())
                            .build())
                    .teacherInfo(TeacherInfo.builder()
                            .firstName(routineRequest.getTeacherInfo().getFirstName())
                            .lastName(routineRequest.getTeacherInfo().getLastName())
                            .role(routineRequest.getTeacherInfo().getRole())
                            .email(routineRequest.getTeacherInfo().getEmail())
                            .phoneNumber(routineRequest.getTeacherInfo().getPhoneNumber())
                            .build())
                    .build();
        }

        Routine newRoutine = Routine.builder()
                .routineName(routineRequest.getRoutineName())
                .routineDate(routineRequest.getRoutineDate())
                .startTime(routineRequest.getStartTime())
                .endTime(routineRequest.getEndTime())
                //(routineRequest.getGroupId())  // Set groupId
                //.(routineRequest.getTeacherId())  // Set teacherId
                .build();

        Routine savedRoutine =routineRepository.save(newRoutine);

        return RoutineResponse.builder()
                .responseCode(RoutineUtils.ROUTINE_CREATION_CODE)
                .responseMessage(RoutineUtils.ROUTINE_CREATION_MESSAGE)
                .build();
    }
}
