package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.RoutineRequest;
import com.task.theeducationalinstitute.dto.RoutineResponse;
import com.task.theeducationalinstitute.entity.Routine;
import org.springframework.stereotype.Service;


public interface RoutineService {

    RoutineResponse createRoutine(RoutineRequest request); //RoutineResponse is the return type of method createRoutine

}
