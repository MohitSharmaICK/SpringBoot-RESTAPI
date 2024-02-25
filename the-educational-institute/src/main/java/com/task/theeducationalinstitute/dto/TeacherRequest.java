package com.task.theeducationalinstitute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherRequest {
    private String firstName;
    private String lastName;
    private LocalDate startDate; //creating startDate and endDate to ensure date range to be passed further in the parameters.
    private LocalDate endDate;
}
