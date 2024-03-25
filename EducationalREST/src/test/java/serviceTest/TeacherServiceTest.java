package serviceTest;


import com.example.EducationalREST.EducationalRestApplication;

import com.example.EducationalREST.Exceptions.TeacherNotValidException;
import com.example.EducationalREST.entity.Teacher;
import com.example.EducationalREST.repository.TeacherRepository;
import com.example.EducationalREST.service.TeacherServiceImplementation;
import jakarta.persistence.EntityManager;

import jakarta.persistence.StoredProcedureQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EducationalRestApplication.class)
public class TeacherServiceTest {

    @InjectMocks
    private TeacherServiceImplementation teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private EntityManager entityManager;

    //1. Test case for add teacher(successful)

    @Test
    public void testAddTeacher_Success() {
        Teacher teacher = new Teacher(); // Create a sample Teacher object
        teacher.setFirstName("Arun"); // Set required fields
        teacher.setLastName("Rawal"); // Set required fields
        teacher.setEmail("arun.rawal@example.com"); // Set required fields
        teacher.setRole("Tutor"); // Set required fields
        teacher.setPhoneNumber("293838201"); // Set required fields

        // Call the addTeacher method
        ResponseEntity<ResponseEntity> responseEntity = teacherService.addTeacher(teacher);

        // Verify that ResponseEntity.ok().build() is returned
        assertEquals(ResponseEntity.ok().build(), responseEntity);

        // Verify that save method of teacherRepository is called once
        verify(teacherRepository, times(1)).save(teacher);
    }

    //2. Test case for teacher info incomplete

    @Test
    public void testAddTeacher_IncompleteTeacherInfo() {
        Teacher teacher = new Teacher(); // Create a sample Teacher object with incomplete information

        // Call the addTeacher method and verify that it throws TeacherNotValidException
        assertThrows(TeacherNotValidException.class, () -> teacherService.addTeacher(teacher));
    }

    //3.Test case for calulcateTotalWorkHours of a teacher
    @Test
    public void testCalculateTotalWorkHours() {
        // Create sample input parameters
        String firstName = "Arun";
        String lastName = "Rawal";
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);

        // Mock the behavior of entityManager.createStoredProcedureQuery to return a mock StoredProcedureQuery
        StoredProcedureQuery queryMock = mock(StoredProcedureQuery.class);
        when(entityManager.createStoredProcedureQuery("GetTotalWorkloadForTeacher")).thenReturn(queryMock);

        // Mock the behavior of queryMock.getOutputParameterValue to return a mock Double
        when(queryMock.getOutputParameterValue("totalWorkload")).thenReturn(10.5);

        // Call the calculateTotalWorkHours method
        Double totalWorkHours = teacherService.calculateTotalWorkHours(firstName, lastName, startDate, endDate);

        // Verify that the query was executed and the result was returned
        assertEquals(10.5, totalWorkHours, 0.01);
        verify(entityManager).createStoredProcedureQuery("GetTotalWorkloadForTeacher");
        verify(queryMock).execute();
        verify(queryMock).getOutputParameterValue("totalWorkload");
    }
}
