package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.*;
import com.task.theeducationalinstitute.entity.Routine;
import com.task.theeducationalinstitute.entity.Teacher;
import com.task.theeducationalinstitute.repository.RoutineRepository;
import com.task.theeducationalinstitute.repository.TeacherRepository;
import java.time.LocalDate;
import java.util.List;
import com.task.theeducationalinstitute.utils.TeacherUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jdk.jfr.Percentage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImplementation implements TeacherService{

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    private RoutineRepository routineRepository;

    /*Created a list of routines for daterange which takes firstName,lastName,startDate and endDate

     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Routine> getRoutinesForTeacherInDateRange(String firstName, String lastName,
                                                          LocalDate startDate, LocalDate endDate) {

        String jpql = "SELECT r FROM Routine r " +
                      "JOIN r.teacher t " +
                      "WHERE t.firstName = :firstName " +
                       "AND t.lastName = :lastName " +
                        "AND r.routineDate BETWEEN :startDate AND :endDate";
        TypedQuery<Routine> query = entityManager.createQuery(jpql, Routine.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        return query.getResultList();
    }

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
        //retrieve teacher from teacherRepository using provided firstName and lastName
        //Optionals help us to check for uniqueness and using isEmpty() method to verify if it is empty.
        Optional<Teacher> checkTeacher = teacherRepository.findByFirstNameAndLastName(firstName, lastName);
        if(checkTeacher.isPresent()) {
            Teacher teacher = checkTeacher.get(); //retrieves the Teacher object from <Optional> checkTeacher.

            //Working on for specified date range
            List<Routine> routines = getRoutinesForTeacherInDateRange(firstName, lastName, startDate, endDate);


            //Calculating the total work hours from list of workloads
            //Used enhanced for loop to iterate over the routines list. Also, used inbuilt methods getStartTime(), getEndTime(), between(start,end)
            double totalWorkHours = 0.0;
            for (Routine routine : routines) {
                LocalTime startTime = routine.getStartTime();
                LocalTime endTime = routine.getEndTime();
                long minutes = Duration.between(startTime, endTime).toMinutes();
                double hours = minutes/60.0;
                totalWorkHours += hours;
            }
            return totalWorkHours;
        }
        else {
            //if teacher is not found, that will throw runtime exception.
            throw new RuntimeException("Teacher not found for provided first name and last name.");
        }
    }
}
