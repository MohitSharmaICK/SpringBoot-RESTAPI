package com.task.theeducationalinstitute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeacherRequest {
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String phoneNumber;
}
