package com.task.theeducationalinstitute.controller;

import com.task.theeducationalinstitute.dto.TeacherRequest;
import com.task.theeducationalinstitute.service.impl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /*
        The url should go through the teacher id i.e.'{value}' to indicate total-workload of the teacher.
        Using responseEntity to getTeacherWorkload that takes firstName, lastName, startDate, endDate as parameters.
        Implemented try-catch block if parameters are passed properly, the response will be 200 OK.
        Else, catch block is implemented where it indicates badRequest.
     */
    @GetMapping(path="{teacherId}/total-workload")
    public ResponseEntity<Double> getTeacherWorkload(@RequestParam("teacherFirstName") String firstName,
                                                     @RequestParam("teacherLastName") String lastName,
                                                     @RequestParam("startDate")LocalDate startDate,
                                                     @RequestParam("endDate") LocalDate endDate)
    {
        try {
            double totalWorkHours = teacherService.getTotalWorkHours(firstName, lastName, startDate, endDate);
            return ResponseEntity.ok(totalWorkHours);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
