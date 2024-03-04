package com.task.theeducationalinstitute.repository;

import com.task.theeducationalinstitute.dto.TeacherInfo;
import com.task.theeducationalinstitute.dto.TeacherResponse;
import com.task.theeducationalinstitute.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    Optional<Teacher> findByFirstNameAndLastName(String firstName, String lastName);

    Boolean existsById(long groupId);

}
