package com.example.EducationalREST.repository;

import com.example.EducationalREST.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {

    @Procedure(name = "calculate_total_workload")
    Double calculateTotalWorkloadByGroupId(@Param("groupId") Long groupId);



    @Procedure(name = "GetTotalWorkloadForTeacher")
    Double getTotalWorkHoursForTeacher(@Param("firstName") String firstName,
                                       @Param("lastName") String lastName,
                                       @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);


}
