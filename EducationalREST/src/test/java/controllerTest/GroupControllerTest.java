package controllerTest;


import com.example.EducationalREST.EducationalRestApplication;
import com.example.EducationalREST.Exceptions.GroupNotValidException;
import com.example.EducationalREST.Exceptions.SimpleResponse;
import com.example.EducationalREST.controller.GroupController;
import com.example.EducationalREST.entity.Group;
import com.example.EducationalREST.repository.GroupRepository;
import com.example.EducationalREST.repository.RoutineRepository;
import com.example.EducationalREST.service.GroupService;
import com.example.EducationalREST.service.GroupServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EducationalRestApplication.class)
public class GroupControllerTest {

    @InjectMocks
    private GroupController groupController;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupService groupService;

    @Mock
    private GroupServiceImplementation groupServiceImplementation;

    @Mock
    private RoutineRepository routineRepository;

    //1.Check a test case where group is created successfully

    @Test
    public void createGroup_valid_Success()
    {
        Group group = new Group(); // Create a sample Group object

        // Mock the behavior of groupServiceImplementation.addGroup to indicate success
        // Use doReturn() since addGroup returns ResponseEntity
        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body("Group created successfully");
        doReturn(responseEntity).when(groupServiceImplementation).addGroup(group);

        // Call the createGroup method
        ResponseEntity response = groupController.createGroup(group);

        // Verify that HttpStatus.CREATED is returned
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Group created successfully.", response.getBody());
    }

    //2.Check a test case where group creation fails(Bad Request by User)
    @Test
    public void createGroup_invalid_Failed() {
        Group group = new Group(); // Create a sample Group object

        // Mock the behavior of groupServiceImplementation.addGroup to throw GroupNotValidException
        doThrow(new GroupNotValidException("Group data is not valid")).when(groupServiceImplementation).addGroup(group);

        // Call the createGroup method
        ResponseEntity responseEntity = groupController.createGroup(group);

        // Verify that HttpStatus.BAD_REQUEST is returned with the appropriate message
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Modify the assertion to check the message inside SimpleResponse object
        assertEquals("Group data is not valid", ((SimpleResponse) responseEntity.getBody()).getMessage());
    }



    //3.2.Check a test case where group creation fails(Internal Server error)

    @Test
    public void testCreateGroup_InternalServerError() {
        Group group = new Group(); // Create a sample Group object

        // Mock the behavior of groupServiceImplementation.addGroup to throw an exception
        doThrow(new RuntimeException("Internal Server Error")).when(groupServiceImplementation).addGroup(group);

        // Call the createGroup method
        ResponseEntity responseEntity = groupController.createGroup(group);

        // Verify that HttpStatus.INTERNAL_SERVER_ERROR is returned with the appropriate message
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Failed to create routine.", responseEntity.getBody());
    }

}
