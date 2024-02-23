package com.task.theeducationalinstitute.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*Using project lombok annotations that decreases boilerplate code such as getter,setter.Also, useful for creating default and all arguments
constructor.@Builder annotation helps to create instance of this particular class if required using the builder pattern.*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name = "routine") //specifying the table name in our database as 'routine'
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long routineId;
    private String routineName;
    private LocalDate routineDate;
    private LocalTime startTime;
    private LocalTime endTime;


}
