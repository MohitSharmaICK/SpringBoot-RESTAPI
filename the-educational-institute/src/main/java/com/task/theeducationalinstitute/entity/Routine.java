package com.task.theeducationalinstitute.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Routine {
    private int routineId;
    private String routineName;
    private LocalDate routineDate;
    private LocalTime startTime;
    private LocalTime endTime;


}
