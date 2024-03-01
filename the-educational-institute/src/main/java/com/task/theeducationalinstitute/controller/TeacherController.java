package com.task.theeducationalinstitute.controller;

import com.task.theeducationalinstitute.dto.*;
import com.task.theeducationalinstitute.entity.Routine;
import com.task.theeducationalinstitute.exception.TeacherNotFoundException;
import com.task.theeducationalinstitute.service.impl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @PostMapping("/create")
    public TeacherResponse saveTeacherInfo(@RequestBody CreateTeacherRequest request) {
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setFirstName(request.getFirstName());
        teacherInfo.setLastName(request.getLastName());
        teacherInfo.setRole(request.getRole());
        teacherInfo.setEmail(request.getEmail());
        teacherInfo.setPhoneNumber(request.getPhoneNumber());
        return teacherService.saveTeacherInfo(teacherInfo);
    }


    /* This is getMapping for finding out the teacher total workload which requests firstname, lastname, startDate and endDate.
      Handled required exceptions using ResponseEntity of type <Double> as total work Hours needs to be returned in double data type.
     */

    @GetMapping(path="{teacherId}/total-workload")
    public ResponseEntity<Double> getTeacherWorkload(@RequestParam("teacherFirstName") String firstName,
                                                     @RequestParam("teacherLastName") String lastName,
                                                     @RequestParam("startDate") LocalDate startDate,
                                                     @RequestParam("endDate") LocalDate endDate)
    {
        if(firstName == null || lastName == null || startDate == null || endDate == null)
        {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            double totalWorkHours = teacherService.getTotalWorkHours(firstName, lastName, startDate, endDate);
            return ResponseEntity.ok(totalWorkHours);
        } catch (TeacherNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
