package com.example.EducationalREST.controller;


import com.example.EducationalREST.Exceptions.GroupNotValidException;
import com.example.EducationalREST.Exceptions.RoutineNotValidException;
import com.example.EducationalREST.Exceptions.SimpleResponse;
import com.example.EducationalREST.entity.Group;
import com.example.EducationalREST.entity.Routine;
import com.example.EducationalREST.repository.GroupRepository;
import com.example.EducationalREST.repository.RoutineRepository;
import com.example.EducationalREST.service.GroupServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/group")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupServiceImplementation groupServiceImplementation;

    @Autowired
    private RoutineRepository routineRepository;

    @PostMapping("/create")
    public ResponseEntity createGroup(@RequestBody Group group)
    {
        try {
            groupServiceImplementation.addGroup(group);
            return ResponseEntity.status(HttpStatus.CREATED).body("Group created successfully.");
        } catch (GroupNotValidException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getSimpleResponse());
        } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create routine.");
        }
    }


    @GetMapping(path = "{groupId}/total-groupworkload")
    public ResponseEntity<Double> getGroupWorkload(@PathVariable Long groupId) {
        // Check if the groupId exists
        if (!groupServiceImplementation.groupExists(groupId)) {
            return ResponseEntity.notFound().build();
        }
        Double totalWorkload = groupServiceImplementation.calculateTotalWorkload(groupId);
        if(totalWorkload != null) {
            return ResponseEntity.ok(totalWorkload);
        } else {
            return ResponseEntity.ok(0.0); // or any other appropriate default value
        }
    }
}
