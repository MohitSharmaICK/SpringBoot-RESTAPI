package com.task.theeducationalinstitute.entity;

import jakarta.persistence.*;
import lombok.*;

/*Using project lombok annotations that decreases boilerplate code such as getter,setter.Also, useful for creating default and all arguments
constructor.@Builder annotation helps to create instance of this particular class if required using the builder pattern.*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity//specifying the table name as 'group' in our database.
@Table(name = "student_group") //using table name as 'student-group' as 'group' name conflicts with SQL reserved word[GROUP].
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //using strategy that helps to generate a new id with incremented by 1.
    private long groupId;
    private String specialization;      //specialization such as "Computing"/"Networking"/"Multimedia" and so on.
    private String gradeLevel;

}
