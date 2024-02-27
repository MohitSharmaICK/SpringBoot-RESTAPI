package com.task.theeducationalinstitute.controller;

import com.task.theeducationalinstitute.dto.RoutineRequest;
import com.task.theeducationalinstitute.dto.RoutineResponse;
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
                .routineDate(LocalDate.now())
                .teacherId(101) // Replace teacherId with an actual teacher ID
                .groupId(1) // Replace groupId with an actual group ID
                .build(); // Replace groupId with an actual group ID

        // Mock service response
        RoutineResponse response = new RoutineResponse();
        response.setResponseMessage("Routine created successfully");

        // Mock service method
        when(routineService.createRoutine(any(RoutineRequest.class))).thenReturn(response);

        // Performing the controller call
        RoutineResponse entity = routineController.createRoutine(request);

        // Verifying the response
        assertNotNull(entity);
        assertEquals("Routine created successfully", entity.getResponseMessage());

        // Verify that the service method is called with the correct argument
        verify(routineService).createRoutine(request);
    }
}