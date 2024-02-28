package com.task.theeducationalinstitute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoutineRequest {
    private LocalDate routineDate;
    private String routineName;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long groupId;
    private Long teacherId;
}
