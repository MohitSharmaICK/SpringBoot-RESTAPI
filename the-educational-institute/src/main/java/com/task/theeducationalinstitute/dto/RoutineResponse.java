package com.task.theeducationalinstitute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoutineResponse {

    private String responseCode;
    private String responseMessage;
    private Long teacherId;
    private Long groupId;

}
