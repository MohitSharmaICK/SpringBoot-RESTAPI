package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.GroupInfo;
import com.task.theeducationalinstitute.dto.GroupResponse;
import com.task.theeducationalinstitute.entity.Group;
import com.task.theeducationalinstitute.entity.Routine;
import com.task.theeducationalinstitute.exception.GroupNotFoundException;
import com.task.theeducationalinstitute.repository.GroupRepository;
import com.task.theeducationalinstitute.utils.GroupUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImplementation implements GroupService {

    @Autowired
    GroupRepository groupRepository;


    /*
    Implemented createGroup method of the service interface that takes groupInfo as object; checked if any of the members of groupInfo is null, if it is then throw a custom response code and message;
    Finally, adding new object of Group(unique) to the database using save method of Repository and displaying custom response from GroupUtils class.
     */
    @Override
    public GroupResponse createGroup(GroupInfo groupInfo) {
        if (groupInfo.getSpecialization() == null || groupInfo.getGradeLevel() == null) {
            return GroupResponse.builder()
                    .responseCode(GroupUtils.ERROR_CODE)
                    .responseMessage(GroupUtils.ERROR_MESSAGE)
                    .build();
        }
        try {
            Group newGroup = Group.builder()
                    .gradeLevel(groupInfo.getGradeLevel())
                    .specialization(groupInfo.getSpecialization())
                    .build();

            Group savedGroup = groupRepository.save(newGroup);
            // Returning a response indicating successful creation from util package
            return GroupResponse.builder()
                    .responseCode(GroupUtils.GROUP_CREATED)
                    .responseMessage(GroupUtils.GROUP_CREATION_MESSAGE)
                    .gradeLevel(savedGroup.getGradeLevel())
                    .specialization(savedGroup.getSpecialization())
                    .build();
        } catch (Exception ex) {
            return GroupResponse.builder()
                    .responseCode(GroupUtils.ERROR_CODE)
                    .responseMessage(GroupUtils.ERROR_MESSAGE)
                    .build();

        }
    }


    /* A method that calculatesGroupTotalWorkload which takes groupId as parameter.Here, at first we need to identify groupId and check if groupId is already
    present in our database; if it exists; we retrieve list of routines from which we will further extract the startTime,endTime as we are required to calculate
    totalWorkLoad in hours.Use of some inbuilt methods for that, and finally returning the totalWorkload as double return type. If the groupId doesn't exist,
    a custom GroupNotFoundException is thrown.
     */
    @Override
    public double calculateGroupTotalWorkload(long groupId) {
       Optional<Group> checkGroup = groupRepository.findByGroupId(groupId);
       if(checkGroup.isPresent())
       {
           Group group = checkGroup.get();
           List<Routine> routineList = group.getRoutines();

           // Initialize total work hours
           double totalWorkloadHours = 0.0;
           for (Routine routine : routineList) {                   //Iterating through routineList using for-each loop.
               LocalTime startTime = routine.getStartTime();
               LocalTime endTime = routine.getEndTime();
               Duration duration = Duration.between(startTime, endTime);
               double hours = duration.toMinutes() / 60.0;
               totalWorkloadHours += hours;
           }
           return totalWorkloadHours;
       } else {
           throw new GroupNotFoundException("Group not found with ID: " + groupId);
        }
       }
    }
