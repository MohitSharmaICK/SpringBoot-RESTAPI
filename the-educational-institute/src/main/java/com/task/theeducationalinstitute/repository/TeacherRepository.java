package com.task.theeducationalinstitute.repository;

import com.task.theeducationalinstitute.dto.TeacherResponse;
import com.task.theeducationalinstitute.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    double getTotalWorkHours(String firstName, String lastName, LocalDate startDate, LocalDate endDate);

    Boolean findByFirstAndLastName(String firstName, String lastName);

}
