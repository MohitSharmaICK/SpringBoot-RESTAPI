package com.task.theeducationalinstitute.controller;

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
    RoutineService routineService;

    @PostMapping
    public RoutineResponse createRoutine(@RequestBody RoutineRequest routineRequest)
    {
        return routineService.createRoutine(routineRequest);
    }
}
