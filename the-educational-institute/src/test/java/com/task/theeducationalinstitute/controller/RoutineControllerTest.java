package com.task.theeducationalinstitute.controller;

import com.task.theeducationalinstitute.dto.*;
import com.task.theeducationalinstitute.service.impl.RoutineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(MockitoJUnitRunner.class)
public class RoutineControllerTest {

    @InjectMocks
    private RoutineController routineController;

    @Mock
    private RoutineService routineService;

    @Test
    public void testCreateRoutine() {
        // Mock request body
        RoutineRequest request = new RoutineRequest().builder()
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .routineName("Digital Logic")
                .routineDate(LocalDate.now())
                .teacherId(102)
                .groupId(2)
                .build();

        // Mock service response
        RoutineResponse response = new RoutineResponse();
        response.setResponseMessage("Routine created successfully");

        // Mock service method
        when(routineService.createRoutine(any(RoutineRequest.class))).thenReturn(response);

        // Performing the controller call
        RoutineResponse entity = routineController.createRoutine(CreateRoutineRequest.builder()
                .routineDate(LocalDate.now()) // Set routine date to current date
                .routineName("Digital Logic") // Set routine name to an example value
                .startTime(LocalTime.of(9, 0)) // Set start time to 9:00 AM
                .endTime(LocalTime.of(10, 0)) // Set end time to 10:00 AM
                .teacherId(request.getTeacherId())
                .groupId(request.getGroupId())
                .build());

        // Verifying the response
        assertNotNull(entity);
        assertEquals("Routine created successfully", entity.getResponseMessage());

        // Verify that the service method is called with the correct argument
        verify(routineService).createRoutine(request);
    }
}