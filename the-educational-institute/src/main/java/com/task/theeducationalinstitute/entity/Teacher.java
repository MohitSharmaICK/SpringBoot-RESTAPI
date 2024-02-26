package com.task.theeducationalinstitute.entity;

import com.task.theeducationalinstitute.utils.TeacherUtils;
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

@Entity(name="teacher") //specifying the table name as 'teacher' in our database.
public class Teacher {
    @Id
    @GeneratedValue(generator ="teacher_gen",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "teacher_gen", sequenceName = "teacher_seq", initialValue = 100, allocationSize = 1)
    private long teacherId;
    private String firstName;
    private String lastName;
    private String role;            //role indicates if the teacher would be 'tutor' or 'lecturer' for now.
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "teacher")
    private List<Routine> routines;
}
