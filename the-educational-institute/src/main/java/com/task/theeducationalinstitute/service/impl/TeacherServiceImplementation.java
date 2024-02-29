package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.*;
import com.task.theeducationalinstitute.entity.Routine;
import com.task.theeducationalinstitute.entity.Teacher;
import com.task.theeducationalinstitute.exception.TeacherNotFoundException;
import com.task.theeducationalinstitute.repository.RoutineRepository;
import com.task.theeducationalinstitute.repository.TeacherRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import com.task.theeducationalinstitute.utils.TeacherUtils;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TeacherServiceImplementation implements TeacherService{

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @PersistenceContext
    private EntityManager entityManager;
//    @Override
//    public List<Routine> getRoutinesForTeacherInDateRange(String firstName, String lastName,
//                                                          LocalDate startDate, LocalDate endDate) {
//        String sql = "SELECT r.* FROM routine r " +
//                "JOIN teacher t ON r.teacher_id = t.teacher_id " +
//                "WHERE t.first_name = :firstName " +
//                "AND t.last_name = :lastName " +
//                "AND r.routine_date BETWEEN (" +
//                "SELECT MIN(routine_date) FROM routine WHERE routine_date BETWEEN :startDate AND :endDate" +
//                ") AND (" +
//                "SELECT MAX(routine_date) FROM routine WHERE routine_date BETWEEN :startDate AND :endDate" +
//                ")";
//
//        Query query = entityManager.createNativeQuery(sql, Routine.class);
//        query.setParameter("firstName", firstName);
//        query.setParameter("lastName", lastName);
//        query.setParameter("startDate", startDate);
//        query.setParameter("endDate", endDate);
//
//        return query.getResultList();
//    }



    /*
        Creating a teacher means saving the teacher information in our database.
        Also, we will check if routine already exists.[Optionally, using a response code and message for now.]
        Can be replaced by throwing an exception as well.
        --> throw new RuntimeException("Teacher already exists.");
     */
    @Override
    public TeacherResponse saveTeacherInfo(TeacherInfo teacherInfo) {
        if (teacherInfo.getFirstName() == null || teacherInfo.getLastName() == null || teacherInfo.getEmail() == null || teacherInfo.getRole() == null || teacherInfo.getPhoneNumber() == null) {
            return TeacherResponse.builder()
                    .responseCode(TeacherUtils.TEACHER_NULLINFO_CODE)
                    .responseMessage(TeacherUtils.TEACHER_NULLINFO_MESSAGE)
                    .build();
        }
        try {
            Teacher newTeacher = Teacher.builder()
                    .firstName(teacherInfo.getFirstName())
                    .lastName(teacherInfo.getLastName())
                    .role(teacherInfo.getRole())
                    .email(teacherInfo.getEmail())
                    .phoneNumber(teacherInfo.getPhoneNumber())
                    .build();

            Teacher savedTeacher = teacherRepository.save(newTeacher);
            return TeacherResponse.builder()
                    .firstName(savedTeacher.getFirstName())
                    .lastName(savedTeacher.getLastName())
                    .responseCode(TeacherUtils.TEACHER_CREATION_CODE)
                    .responseMessage(TeacherUtils.TEACHER_CREATED_MESSAGE)
                    .build();
        } catch (Exception ex) {
            return TeacherResponse.builder()
                    .responseCode(TeacherUtils.TEACHER_CREATION_ERROR_CODE)
                    .responseMessage(TeacherUtils.TEACHER_CREATION_ERROR_MESSAGE)
                    .build();
        }
    }



    /*
    This is a method getTotalWorkHours that takes firstName, lastName, startDate and endDate as parameters. This method
    has been defined in TeacherService and now being implemented in TeacherServiceImplementation.
    Controller for this has been defined in TeacherController class.
     */
    @Override
    public double getTotalWorkHours(String firstName, String lastName, LocalDate startDate, LocalDate endDate) {
        Optional<Teacher> optionalTeacher = teacherRepository.findByFirstNameAndLastName(firstName, lastName);

        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            List<Routine> allRoutines = routineRepository.findByTeacher(teacher);

            double totalWorkHours = 0.0;

            // Iterate over each routine and check if it falls within the provided date range
            for (Routine routine : allRoutines) {
                LocalDate routineDate = routine.getRoutineDate();
                if (routineDate.isEqual(startDate) || routineDate.isEqual(endDate) ||
                        (routineDate.isAfter(startDate) && routineDate.isBefore(endDate))) {

                    // Calculating the work hours for routines within the date range
                    LocalTime startTime = routine.getStartTime();
                    LocalTime endTime = routine.getEndTime();
                    long minutes = Duration.between(startTime, endTime).toMinutes();
                    double hours = minutes / 60.0;
                    totalWorkHours += hours;
                }
            }

            return totalWorkHours;
        } else {
            throw new TeacherNotFoundException("Teacher not found for provided first name and last name.");
        }
    }

}


