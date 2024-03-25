package controllerTest;


import com.example.EducationalREST.EducationalRestApplication;
import com.example.EducationalREST.Exceptions.SimpleResponse;
import com.example.EducationalREST.Exceptions.TeacherNotValidException;
import com.example.EducationalREST.controller.TeacherController;
import com.example.EducationalREST.entity.Teacher;
import com.example.EducationalREST.repository.TeacherRepository;
import com.example.EducationalREST.service.TeacherServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EducationalRestApplication.class)
public class TeacherControllerTest {

    @InjectMocks
    private TeacherController teacherController;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherServiceImplementation teacherServiceImplementation;

    //1. Test for add teacher success

    @Test
    public void testAddTeacher_Success() {
        Teacher teacher = new Teacher(); // Create a sample Teacher object

        // Mock the behavior of teacherServiceImplementation.addTeacher to indicate success
        // Use doReturn() since addTeacher doesn't return void
        doReturn(ResponseEntity.status(HttpStatus.CREATED).body("Teacher created successfully.")).when(teacherServiceImplementation).addTeacher(teacher);

        // Call the addTeacher method
        ResponseEntity responseEntity = teacherController.addTeacher(teacher);

        // Verify that HttpStatus.CREATED is returned
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Teacher created successfully.", responseEntity.getBody());
    }

    //2. Test for bad request from user

    @Test
    public void testAddTeacher_BadRequest() {
        Teacher teacher = new Teacher(); // Create a sample Teacher object

        // Mock the behavior of teacherServiceImplementation.addTeacher to throw TeacherNotValidException
        doThrow(new TeacherNotValidException("Teacher data is not valid")).when(teacherServiceImplementation).addTeacher(teacher);

        // Call the addTeacher method
        ResponseEntity responseEntity = teacherController.addTeacher(teacher);

        // Verify that HttpStatus.BAD_REQUEST is returned with the appropriate message
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Teacher data is not valid", ((SimpleResponse) responseEntity.getBody()).getMessage());
    }

    //3. Test for internal server error

    @Test
    public void testAddTeacher_InternalServerError() {
        Teacher teacher = new Teacher(); // Create a sample Teacher object

        // Mock the behavior of teacherServiceImplementation.addTeacher to throw TeacherNotValidException
        doThrow(new TeacherNotValidException("Teacher data is not valid")).when(teacherServiceImplementation).addTeacher(teacher);

        // Call the addTeacher method
        ResponseEntity responseEntity = teacherController.addTeacher(teacher);

        // Verify that HttpStatus.BAD_REQUEST is returned with the appropriate message
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Teacher data is not valid", ((SimpleResponse) responseEntity.getBody()).getMessage());
    }



}
