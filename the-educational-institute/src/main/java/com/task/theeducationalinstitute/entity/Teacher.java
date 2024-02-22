package com.task.theeducationalinstitute.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    private int teacherId;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String phoneNumber;
}
