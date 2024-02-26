package com.task.theeducationalinstitute.repository;

import com.task.theeducationalinstitute.dto.TeacherInfo;
import com.task.theeducationalinstitute.dto.TeacherResponse;
import com.task.theeducationalinstitute.dto.Workload;
import com.task.theeducationalinstitute.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    double getTotalWorkHours(String firstName, String lastName, LocalDate startDate, LocalDate endDate);

    Optional<Teacher> findByFirstNameAndLastName(String firstName, String lastName);

    List<Workload> findByNameAndDateRange(Teacher teacher, LocalDate startDate, LocalDate endDate);

}
