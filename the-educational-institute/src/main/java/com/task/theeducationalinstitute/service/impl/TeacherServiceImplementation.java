package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.TeacherInfo;
import com.task.theeducationalinstitute.dto.TeacherRequest;
import com.task.theeducationalinstitute.dto.TeacherResponse;
import com.task.theeducationalinstitute.dto.Workload;
import com.task.theeducationalinstitute.entity.Teacher;
import com.task.theeducationalinstitute.exception.TeacherNotFoundException;
import com.task.theeducationalinstitute.repository.TeacherRepository;
import com.task.theeducationalinstitute.utils.TeacherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImplementation implements TeacherService{

    @Autowired
    TeacherRepository teacherRepository;

    /*
        Creating a teacher means saving the teacher information in our database.
        Also, we will check if routine already exists.[Optionally, using a response code and message for now.]
        Can be replaced by throwing an exception as well.
        --> throw new RuntimeException("Teacher already exists.");
     */
    @Override
    public TeacherResponse saveTeacherInfo(TeacherInfo teacherInfo) {
        if(teacherInfo.getTeacherId() != null)
        {
            Optional<Teacher> existingTeacher = teacherRepository.findById(teacherInfo.getTeacherId());
            if(existingTeacher.isPresent())
            {
                return TeacherResponse.builder()
                        .responseCode(TeacherUtils.TEACHER_EXISTS_CODE)
                        .responseMessage(TeacherUtils.TEACHER_EXISTS_MESSAGE)
                        .firstName(teacherInfo.getFirstName())
                        .lastName(teacherInfo.getLastName())
                        .build();
            }
        }
            Teacher newTeacher = Teacher.builder()
                    .firstName(teacherInfo.getFirstName())
                    .lastName(teacherInfo.getLastName())
                    .role(teacherInfo.getRole())
                    .email(teacherInfo.getEmail())
                    .phoneNumber(teacherInfo.getPhoneNumber())
                    .build();

            Teacher savedTeacher = teacherRepository.save(newTeacher);
            return TeacherResponse.builder()
                    .responseCode(TeacherUtils.TEACHER_CREATION_CODE)
                    .responseMessage(TeacherUtils.TEACHER_CREATED_MESSAGE)
                    .build();
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
        if(checkTeacher.isEmpty())
        {
            //if teacher is not found, made a custom exception class in exception package. That will throw runtime exception.
            throw new TeacherNotFoundException("Teacher Not Found");
        }
        Teacher teacher = checkTeacher.get(); //retrieves the Teacher object from <Optional> checkTeacher.

        //Working on for specified date range
        List<Workload> workloads = teacherRepository.findByNameAndDateRange(teacher,startDate,endDate);

        //Calculating the total work hours from list of workloads
        return workloads.stream()
                .mapToDouble(Workload::getHours)
                .sum();
    }
}
