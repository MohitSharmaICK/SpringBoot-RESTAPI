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
    private TeacherInfo teacherInfo;    //teacherInfo[a class]consists of teacher name(first,last), id and role.
    private GroupInfo groupInfo;        //GroupInfo[a class]consists of specialization and gradeLevel.

}
