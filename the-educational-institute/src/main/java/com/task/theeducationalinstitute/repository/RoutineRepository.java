package com.task.theeducationalinstitute.repository;


import com.task.theeducationalinstitute.entity.Routine;
import com.task.theeducationalinstitute.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoutineRepository extends JpaRepository<Routine,Long> {


    Boolean existsByGroup_GroupIdAndTeacher_TeacherId(@Param("groupId")long groupId, @Param("teacherId") long teacherId);



    List<Routine> findByTeacherAndRoutineDateBetween(Teacher teacher, LocalDate startDate, LocalDate endDate);

    //created a method that takes teacher entity object, startDate and endDate as parameter which will later fetch on list of routines for that particular teacher.
    //List<Routine> findByTeacher(Teacher teacher);


}
