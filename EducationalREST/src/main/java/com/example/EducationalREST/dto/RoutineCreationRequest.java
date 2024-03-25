package com.example.EducationalREST.dto;


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
public class RoutineCreationRequest {
    private LocalDate routineDate;
    private String routineName;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long groupId;
    private Long teacherId;
}
