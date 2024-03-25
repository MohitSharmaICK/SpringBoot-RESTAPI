package com.example.EducationalREST.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Entity//specifying the table name as 'group' in our database.
@Table(name = "student_group")  //using table name as 'student-group' as 'group' name conflicts with SQL reserved word[GROUP].
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;
    private String specialization;      //specialization such as "Computing"/"Networking"/"Multimedia" and so on.
    private String gradeLevel;
}
