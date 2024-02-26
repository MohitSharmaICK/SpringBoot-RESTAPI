package com.task.theeducationalinstitute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class GroupResponse {
    private String responseCode;
    private String responseMessage;
    private String specialization;
    private String gradeLevel;

}
