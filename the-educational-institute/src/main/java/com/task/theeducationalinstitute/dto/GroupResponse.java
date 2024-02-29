package com.task.theeducationalinstitute.dto;

import com.task.theeducationalinstitute.utils.GroupUtils;
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

    public static GroupResponse error(String groupInfoCannotBeNull) {
        return GroupResponse.builder()
                .responseCode(GroupUtils.ERROR_CODE)
                .responseMessage(GroupUtils.ERROR_MESSAGE)
                .build();
    }
}
