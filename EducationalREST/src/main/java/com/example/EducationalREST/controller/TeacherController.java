package com.example.EducationalREST.controller;


import com.example.EducationalREST.Exceptions.RoutineNotValidException;
import com.example.EducationalREST.Exceptions.SimpleResponse;
import com.example.EducationalREST.Exceptions.TeacherNotFoundException;
import com.example.EducationalREST.Exceptions.TeacherNotValidException;
import com.example.EducationalREST.entity.Teacher;
import com.example.EducationalREST.repository.TeacherRepository;
import com.example.EducationalREST.service.TeacherServiceImplementation;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/teacher")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherServiceImplementation teacherServiceImplementation;

    @PostMapping("/create")
    public ResponseEntity addTeacher(@RequestBody Teacher teacher) {
        try {
            teacherServiceImplementation.addTeacher(teacher);
            return ResponseEntity.status(HttpStatus.CREATED).body("Teacher created successfully.");
        } catch (TeacherNotValidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getSimpleResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create teacher.");
        }
    }

    @GetMapping(path = "{teacherId}/total-workload")
    public ResponseEntity<Double> getTeacherWorkload(@RequestParam("teacherFirstName") String firstName,
                                                     @RequestParam("teacherLastName") String lastName,
                                                     @RequestParam("startDate") LocalDate startDate,
                                                     @RequestParam("endDate") LocalDate endDate) {
        if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName) ||
                StringUtils.isEmpty(String.valueOf(startDate)) || StringUtils.isEmpty(String.valueOf(endDate))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try {
            double totalWorkload = teacherServiceImplementation.calculateTotalWorkHours(firstName, lastName, startDate, endDate);
            return ResponseEntity.ok(totalWorkload);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}




