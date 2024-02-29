package com.task.theeducationalinstitute.controller;

import com.task.theeducationalinstitute.dto.CreateGroupRequest;
import com.task.theeducationalinstitute.dto.GroupInfo;
import com.task.theeducationalinstitute.dto.GroupResponse;
import com.task.theeducationalinstitute.repository.GroupRepository;
import com.task.theeducationalinstitute.service.impl.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/create")
    public GroupResponse createGroup(@RequestBody CreateGroupRequest request) {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setSpecialization(request.getSpecialization());
        groupInfo.setGradeLevel(request.getGradeLevel());
        return groupService.createGroup(groupInfo);
    }

    @GetMapping(path = "{groupId}/total-groupworkload")
    public ResponseEntity<Double> getGroupWorkload(@RequestParam long groupId) {
        try {
            double groupTotalHours = groupService.calculateGroupTotalWorkload(groupId);
            if(groupTotalHours >= 0) {
                return ResponseEntity.ok(groupTotalHours);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

