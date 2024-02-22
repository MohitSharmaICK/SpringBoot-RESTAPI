package com.task.theeducationalinstitute.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
    private long groupId;
    private String specialization;
    private String gradeLevel;

}
