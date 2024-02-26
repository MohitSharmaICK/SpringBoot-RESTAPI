package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.TeacherInfo;
import com.task.theeducationalinstitute.dto.TeacherRequest;
import com.task.theeducationalinstitute.dto.TeacherResponse;
import com.task.theeducationalinstitute.entity.Teacher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

public interface TeacherService {

    TeacherResponse saveTeacherInfo(TeacherInfo teacherInfo);


    public double getTotalWorkHours(String firstName, String lastName, LocalDate startDate, LocalDate endDate);
}
