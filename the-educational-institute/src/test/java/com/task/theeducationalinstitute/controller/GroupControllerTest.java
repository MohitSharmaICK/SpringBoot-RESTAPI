package com.task.theeducationalinstitute.controller;

import com.task.theeducationalinstitute.exception.GroupNotFoundException;
import com.task.theeducationalinstitute.service.impl.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroupControllerTest {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    //1st Test method for controller to get GroupWorkload if group found
    @Test
    public void testGetGroupWorkload_Success() {
        // Mock request parameters
        long groupId = 11;

        // Mock service method call
        double expectedWorkload = 100.0; // Assuming workload for the test case
        when(groupService.calculateGroupTotalWorkload(groupId)).thenReturn(expectedWorkload);

        // Call controller method
        ResponseEntity<Double> responseEntity = groupController.getGroupWorkload(groupId);

        // Verify response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedWorkload, responseEntity.getBody(), 0.001); // Specifying delta for double comparison

    }

    //2nd Test method for controller to get GroupWorkload if group not found
    @Test
    public void testGetGroupWorkload_GroupNotFound() {
        // Mock request parameters
        long groupId = 15;

        // Mock service method call
        when(groupService.calculateGroupTotalWorkload(groupId)).thenThrow(new GroupNotFoundException("Group not found"));

        // Calling controller method
        ResponseEntity<Double> responseEntity = groupController.getGroupWorkload(groupId);

        // Verifying response
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}

