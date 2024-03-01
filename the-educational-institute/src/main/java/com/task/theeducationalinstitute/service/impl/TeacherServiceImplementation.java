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
        Optional<Teacher> checkTeacher = teacherRepository.findByFirstNameAndLastName(firstName, lastName);

        if (checkTeacher.isPresent()) {
            Teacher teacher = checkTeacher.get(); // Get the teacher from the optional

            List<Routine> allRoutines = routineRepository.findByTeacherAndRoutineDateBetween(teacher, startDate, endDate);

            double totalWorkHours = 0.0;

            // Iterate over each routine and calculate the work hours
            for (Routine routine : allRoutines) {
                LocalTime startTime = routine.getStartTime();
                LocalTime endTime = routine.getEndTime();
                long minutes = Duration.between(startTime, endTime).toMinutes();
                double hours = minutes / 60.0;
                totalWorkHours += hours;
            }

            return totalWorkHours;
        } else {
            throw new TeacherNotFoundException("Teacher not found for provided first name and last name.");
        }
    }
}


