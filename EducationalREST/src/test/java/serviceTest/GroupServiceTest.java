package serviceTest;


import com.example.EducationalREST.EducationalRestApplication;
import com.example.EducationalREST.Exceptions.GroupNotValidException;
import com.example.EducationalREST.entity.Group;
import com.example.EducationalREST.repository.GroupRepository;
import com.example.EducationalREST.repository.RoutineRepository;
import com.example.EducationalREST.service.GroupServiceImplementation;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = EducationalRestApplication.class)
public class GroupServiceTest {

    @InjectMocks
    private GroupServiceImplementation groupService;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private RoutineRepository routineRepository;

    @Mock
    private EntityManager entityManager;

    @Test
    public void testAddGroup_Success() {
        Group group = new Group(); // Create a sample Group object
        group.setGradeLevel("10"); // Set required fields
        group.setSpecialization("Maths"); // Set required fields

        // Call the addGroup method
        ResponseEntity responseEntity = groupService.addGroup(group);

        // Verify that ResponseEntity.ok().build() is returned
        assertEquals(ResponseEntity.ok().build(), responseEntity);

        // Verify that save method of groupRepository is called once
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    public void testAddGroup_IncompleteGroupInfo() {
        Group group = new Group(); // Create a sample Group object with incomplete information

        // Call the addGroup method and verify that it throws GroupNotValidException
        assertThrows(GroupNotValidException.class, () -> groupService.addGroup(group));
    }

}
