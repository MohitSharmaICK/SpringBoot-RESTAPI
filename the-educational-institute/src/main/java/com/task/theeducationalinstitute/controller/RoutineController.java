package com.task.theeducationalinstitute.controller;

import com.task.theeducationalinstitute.dto.CreateRoutineRequest;
import com.task.theeducationalinstitute.dto.RoutineRequest;
import com.task.theeducationalinstitute.dto.RoutineResponse;
import com.task.theeducationalinstitute.service.impl.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/routine")
public class RoutineController {

    @Autowired
    private RoutineService routineService;

    @PostMapping("/create")
    public RoutineResponse createRoutine(@RequestBody CreateRoutineRequest createRoutineRequest) {
        RoutineRequest routineRequest = new RoutineRequest();
        routineRequest.setRoutineDate(createRoutineRequest.getRoutineDate());
        routineRequest.setRoutineName(createRoutineRequest.getRoutineName());
        routineRequest.setStartTime(createRoutineRequest.getStartTime());
        routineRequest.setEndTime(createRoutineRequest.getEndTime());
        routineRequest.setTeacherId(createRoutineRequest.getTeacherId());
        routineRequest.setGroupId(createRoutineRequest.getGroupId());
        return routineService.createRoutine(routineRequest);
    }
}
