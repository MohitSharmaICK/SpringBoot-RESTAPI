package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.TeacherRequest;
import com.task.theeducationalinstitute.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TeacherServiceImplementation implements TeacherService{

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public double getTotalWorkHours(String firstName, String lastName, LocalDate startDate, LocalDate endDate) {
        return 0;
    }
}
