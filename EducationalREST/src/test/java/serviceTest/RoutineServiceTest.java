package serviceTest;


import com.example.EducationalREST.EducationalRestApplication;
import com.example.EducationalREST.Exceptions.GroupNotValidException;
import com.example.EducationalREST.Exceptions.RoutineNotValidException;
import com.example.EducationalREST.Exceptions.TeacherNotValidException;
import com.example.EducationalREST.dto.RoutineCreationRequest;
import com.example.EducationalREST.entity.Routine;
import com.example.EducationalREST.repository.GroupRepository;
import com.example.EducationalREST.repository.RoutineRepository;
import com.example.EducationalREST.repository.TeacherRepository;
import com.example.EducationalREST.service.RoutineServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EducationalRestApplication.class)
public class RoutineServiceTest {
    @InjectMocks
    private RoutineServiceImplementation routineService;

    @Mock
    private RoutineRepository routineRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private TeacherRepository teacherRepository;

    //Test case for routine added successfully

    @Test
    public void testAddRoutine_Success() {
        // Create a sample RoutineCreationRequest object
        RoutineCreationRequest request = new RoutineCreationRequest();
        request.setGroupId(1L); // Set required fields
        request.setTeacherId(2L); // Set required fields
        request.setRoutineName("Maths"); // Set required fields
        request.setRoutineDate(LocalDate.now()); // Set required fields
        request.setStartTime(LocalTime.of(9, 0)); // Set required fields
        request.setEndTime(LocalTime.of(10, 0)); // Set required fields

        // Mock the behavior of groupRepository.existsById to return true
        when(groupRepository.existsById(request.getGroupId())).thenReturn(true);

        // Mock the behavior of teacherRepository.existsById to return true
        when(teacherRepository.existsById(request.getTeacherId())).thenReturn(true);

        // Call the addRoutine method
        ResponseEntity responseEntity = routineService.addRoutine(request);

        // Verify that ResponseEntity.ok().build() is returned
        assertEquals(ResponseEntity.ok().build(), responseEntity);

        // Verify that save method of routineRepository is called once with the correct Routine object
        verify(routineRepository, times(1)).save(any(Routine.class));
    }


    //Invalid group id while adding routine
    @Test
    public void testAddRoutine_InvalidGroupId() {
        // Create a sample RoutineCreationRequest object with invalid GroupId
        RoutineCreationRequest request = new RoutineCreationRequest();
        request.setGroupId(1L); // Set required fields


        // Mock the behavior of groupRepository.existsById to return false
        when(groupRepository.existsById(request.getGroupId())).thenReturn(false);

        // Call the addRoutine method and verify that it throws GroupNotValidException
        assertThrows(GroupNotValidException.class, () -> routineService.addRoutine(request));
    }


    //Invalid teacher id while adding routine
    @Test
    public void testAddRoutine_InvalidTeacherId() {
        // Create a sample RoutineCreationRequest object with invalid TeacherId
        RoutineCreationRequest request = new RoutineCreationRequest();
        request.setRoutineDate(LocalDate.now());
        request.setRoutineName("Maths");
        request.setStartTime(LocalTime.of(9, 0));
        request.setEndTime(LocalTime.of(10, 0));
        request.setGroupId(1L);
        request.setTeacherId(2L);

        // Mock the behavior of groupRepository.existsById to return true
        when(groupRepository.existsById(anyLong())).thenReturn(true);

        // Mock the behavior of teacherRepository.existsById to return false
        when(teacherRepository.existsById(request.getTeacherId())).thenReturn(false);

        // Call the addRoutine method and verify that it throws TeacherNotValidException
        assertThrows(TeacherNotValidException.class, () -> routineService.addRoutine(request));
    }

    //4. If the routine is invalid, this test case will check for it
    @Test
    public void testAddRoutine_InvalidRoutineName() {

        RoutineCreationRequest request = new RoutineCreationRequest();
        request.setRoutineName(""); // Set an empty routine name
        request.setRoutineDate(LocalDate.now());
        request.setStartTime(LocalTime.of(9, 0));
        request.setEndTime(LocalTime.of(10, 0));
        request.setGroupId(1L);
        request.setTeacherId(2L);

        // Mock the behavior of groupRepository.existsById to return true
        when(groupRepository.existsById(anyLong())).thenReturn(true);
        // Mock the behavior of teacherRepository.existsById to return true
        when(teacherRepository.existsById(anyLong())).thenReturn(true);

        // Call the addRoutine method
        assertThrows(RoutineNotValidException.class, () -> routineService.addRoutine(request));
    }

}
