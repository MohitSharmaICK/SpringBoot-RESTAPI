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
    Implemented createGroup method of the service interface that takes groupInfo as object; checked if the groupId is null, if it is then throw an exception
    or check again for existing groupId as we cant have duplicateFields in database; verify using findById method of repository and using getGroupId() method.
    Finally, adding new object of Group(unique) to the database using save method of Repository and displaying custom response from GroupUtils class.
     */
    @Override
    public GroupResponse createGroup(GroupInfo groupInfo) {
        if(groupInfo.getGroupId()!=null) {
            Optional<Group> existingGroup = groupRepository.findById(groupInfo.getGroupId());
            if(existingGroup.isPresent())
            {
                //returning a response indicating group already exists from util package
                return GroupResponse.builder()
                        .responseCode(GroupUtils.GROUP_EXISTS_CODE)
                        .responseMessage(GroupUtils.GROUP_EXISTS_MESSAGE)
                        .gradeLevel(groupInfo.getGradeLevel())
                        .specialization(groupInfo.getSpecialization())
                        .build();
            }
            else {
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
            }
        }
        else {
            throw new IllegalArgumentException("Group ID cannot be null");
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
               totalWorkloadHours += duration.toHours(); //using toHours() an inbuilt method.
           }
           return totalWorkloadHours;
       } else {
           throw new GroupNotFoundException("Group not found with ID: " + groupId);
        }
       }
    }
