package com.example.EducationalREST.service;

import com.example.EducationalREST.Exceptions.TeacherNotValidException;
import com.example.EducationalREST.entity.Teacher;
import com.example.EducationalREST.repository.RoutineRepository;
import com.example.EducationalREST.repository.TeacherRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TeacherServiceImplementation implements TeacherService<Teacher, ResponseEntity> {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Override
    public ResponseEntity<ResponseEntity> addTeacher(Teacher teacher) {
        validateTeacher(teacher);

        teacherRepository.save(teacher);
        return ResponseEntity.ok().build();
    }

    @PersistenceContext
    private EntityManager entityManager; // Inject EntityManager

    @Override
    public Double calculateTotalWorkHours(String firstName, String lastName, LocalDate startDate, LocalDate endDate) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("GetTotalWorkloadForTeacher");
        query.registerStoredProcedureParameter("firstName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("lastName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("startDate", LocalDate.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("endDate", LocalDate.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("totalWorkload", Double.class, ParameterMode.OUT);

        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        query.execute();

        Double totalWorkload = (Double) query.getOutputParameterValue("totalWorkload");
        return totalWorkload;
    }




    //Validating so that none of the field is empty while creating a teacher.
    public void validateTeacher(Teacher teacher) {
        if (StringUtils.isBlank(teacher.getFirstName()) || StringUtils.isBlank(teacher.getLastName())
                || StringUtils.isBlank(teacher.getEmail()) || StringUtils.isBlank(teacher.getRole())
                || StringUtils.isBlank(teacher.getPhoneNumber())) {
            throw new TeacherNotValidException("Teacher information is incomplete");
        }
    }
}
