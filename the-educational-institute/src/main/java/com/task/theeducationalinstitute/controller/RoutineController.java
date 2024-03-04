package com.task.theeducationalinstitute.controller;

import com.task.theeducationalinstitute.dto.CreateRoutineRequest;
import com.task.theeducationalinstitute.dto.RoutineRequest;
import com.task.theeducationalinstitute.dto.RoutineResponse;
import com.task.theeducationalinstitute.exception.GroupNotFoundException;
import com.task.theeducationalinstitute.exception.TeacherNotFoundException;
import com.task.theeducationalinstitute.service.impl.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/routine")
public class RoutineController {

    @Autowired
    private RoutineService routineService;

    @PostMapping("/create")
    public RoutineResponse createRoutine(@RequestBody CreateRoutineRequest createRoutineRequest) throws ResponseStatusException {
        try {
            RoutineRequest routineRequest = new RoutineRequest();
            routineRequest.setRoutineDate(createRoutineRequest.getRoutineDate());
            routineRequest.setRoutineName(createRoutineRequest.getRoutineName());
            routineRequest.setStartTime(createRoutineRequest.getStartTime());
            routineRequest.setEndTime(createRoutineRequest.getEndTime());
            routineRequest.setTeacherId(createRoutineRequest.getTeacherId());
            routineRequest.setGroupId(createRoutineRequest.getGroupId());

            RoutineResponse response = routineService.createRoutine(routineRequest);

            return response;

            // Code that throws Exception if GroupNotFound or TeacherNotFound
        } catch (GroupNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found: " + e.getMessage());
        } catch (TeacherNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher not found: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing the request", e);
        }
    }
}
