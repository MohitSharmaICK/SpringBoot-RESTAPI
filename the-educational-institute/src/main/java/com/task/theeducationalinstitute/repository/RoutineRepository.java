package com.task.theeducationalinstitute.repository;

import com.task.theeducationalinstitute.dto.RoutineRequest;
import com.task.theeducationalinstitute.dto.RoutineResponse;
import com.task.theeducationalinstitute.entity.Group;
import com.task.theeducationalinstitute.entity.Routine;
import com.task.theeducationalinstitute.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RoutineRepository extends JpaRepository<Routine,Long> {

    Boolean existsByGroup_GroupIdAndTeacher_TeacherId(@Param("groupId")long groupId, @Param("teacherId") long teacherId);
}
