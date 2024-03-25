package controllerTest;


import com.example.EducationalREST.EducationalRestApplication;
import com.example.EducationalREST.Exceptions.RoutineNotValidException;
import com.example.EducationalREST.Exceptions.SimpleResponse;
import com.example.EducationalREST.controller.RoutineController;
import com.example.EducationalREST.dto.RoutineCreationRequest;
import com.example.EducationalREST.repository.RoutineRepository;
import com.example.EducationalREST.service.RoutineServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EducationalRestApplication.class)
public class RoutineControllerTest {

    @InjectMocks
    private RoutineController routineController;

    @Mock
    private RoutineRepository routineRepository;

    @Mock
    private RoutineServiceImplementation  routineServiceImplementation;

    //1. Test for valid routine add(success)

    @Test
    public void testAddRoutine_Success() {
        RoutineCreationRequest request = new RoutineCreationRequest(); // Create a sample request object

        // Mock the behavior of routineServiceImplementation.addRoutine to indicate success
        // Use doReturn() since addRoutine doesn't return void
        doReturn(ResponseEntity.status(HttpStatus.CREATED).body("Routine created successfully.")).when(routineServiceImplementation).addRoutine(request);

        // Call the addRoutine method
        ResponseEntity responseEntity = routineController.addRoutine(request);

        // Verify that HttpStatus.CREATED is returned
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Routine created successfully.", responseEntity.getBody());
    }

    //2.//1. Test for bad request(add routine failed)

    @Test
    public void testAddRoutine_BadRequest() {
        RoutineCreationRequest request = new RoutineCreationRequest(); // Create a sample request object

        // Mock the behavior of routineServiceImplementation.addRoutine to throw RoutineNotValidException
        doThrow(new RoutineNotValidException("Routine data is not valid")).when(routineServiceImplementation).addRoutine(request);

        // Call the addRoutine method
        ResponseEntity responseEntity = routineController.addRoutine(request);

        // Verify that HttpStatus.BAD_REQUEST is returned with the appropriate message
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Routine data is not valid", ((SimpleResponse) responseEntity.getBody()).getMessage());
    }

    //3.Test for invalid routine add(internal server error)
    @Test
    public void testAddRoutine_InternalServerError() {
        RoutineCreationRequest request = new RoutineCreationRequest(); // Create a sample request object

        // Mock the behavior of routineServiceImplementation.addRoutine to throw an exception
        doThrow(new RuntimeException("Internal Server Error")).when(routineServiceImplementation).addRoutine(request);

        // Call the addRoutine method
        ResponseEntity responseEntity = routineController.addRoutine(request);

        // Verify that HttpStatus.INTERNAL_SERVER_ERROR is returned with the appropriate message
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Failed to create routine.", responseEntity.getBody());
    }
}
