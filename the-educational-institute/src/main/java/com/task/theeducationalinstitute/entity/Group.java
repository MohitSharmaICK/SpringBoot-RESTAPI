package com.task.theeducationalinstitute.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;
    private String specialization;      //specialization such as "Computing"/"Networking"/"Multimedia" and so on.
    private String gradeLevel;

    @OneToMany(mappedBy = "group")
    private List<Routine> routines;

}
