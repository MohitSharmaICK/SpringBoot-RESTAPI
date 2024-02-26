package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.TeacherInfo;
import com.task.theeducationalinstitute.dto.TeacherRequest;
import com.task.theeducationalinstitute.dto.TeacherResponse;
import com.task.theeducationalinstitute.entity.Teacher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

public interface TeacherService {

    //Method that will save teacher information in our database and respond with user defined Response created in Utils package.
    TeacherResponse saveTeacherInfo(TeacherInfo teacherInfo);

    //Method that returns a teacher's total work hours(workload) in double. It takes teacher's name's[first & last] as well as Date's[start & end].
    public double getTotalWorkHours(String firstName, String lastName, LocalDate startDate, LocalDate endDate);
}
