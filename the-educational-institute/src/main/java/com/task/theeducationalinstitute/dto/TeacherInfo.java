package com.task.theeducationalinstitute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/*Created to build the objects using builder annotation in Routine service implementation and wherever required.*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInfo {
    private Long teacherId; //unique identifier
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String phoneNumber;
}
