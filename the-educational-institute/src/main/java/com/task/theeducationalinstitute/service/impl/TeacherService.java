package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.TeacherRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

public interface TeacherService {

    public double getTotalWorkHours(String firstName, String lastName, LocalDate startDate, LocalDate endDate);
}
