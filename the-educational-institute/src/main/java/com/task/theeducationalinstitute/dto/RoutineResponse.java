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
    private Long teacherId;    //teacherInfo[a class]consists of teacher name(first,last), id and role.
    private Long groupId;        //GroupInfo[a class]consists of specialization and gradeLevel.

}
