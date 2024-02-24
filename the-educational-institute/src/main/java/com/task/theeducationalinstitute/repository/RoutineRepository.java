package com.task.theeducationalinstitute.repository;

import com.task.theeducationalinstitute.dto.RoutineRequest;
import com.task.theeducationalinstitute.dto.RoutineResponse;
import com.task.theeducationalinstitute.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine,Long> {

    Boolean existsByGroupIdTeacherId(long groupId,long teacherId);



}
