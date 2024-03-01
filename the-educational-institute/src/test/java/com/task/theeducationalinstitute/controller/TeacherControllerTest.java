package com.task.theeducationalinstitute.controller;

import com.task.theeducationalinstitute.service.impl.TeacherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @Test
    public void testGetTeacherWorkload() {
        // Arrange
        String firstName = "Nathan";
        String lastName = "Ake";
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);
        double expectedWorkload = 50.0; // Assuming expected workload for the test case in double datatype

        // Mock the behavior of teacherService.getTotalWorkHours() method
        when(teacherService.getTotalWorkHours(firstName, lastName, startDate, endDate)).thenReturn(expectedWorkload);

        // Act
        ResponseEntity<Double> responseEntity = teacherController.getTeacherWorkload(firstName, lastName, startDate, endDate);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedWorkload, responseEntity.getBody(), 0.01);
    }

    //Test case for getTeacherWorkload
    @Test
    public void testGetTeacherWorkload_InvalidInput() {
        // Arrange: Define invalid input parameters
        String firstName = "Molly";
        String lastName = "Williams";
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);

        // Act
        ResponseEntity<Double> responseEntity = teacherController.getTeacherWorkload(firstName, lastName, startDate, endDate);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}

