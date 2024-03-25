package com.example.EducationalREST.service;

import com.example.EducationalREST.entity.Teacher;
import com.example.EducationalREST.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface TeacherService <E, T> {
    ResponseEntity<T> addTeacher(E entity);


    public Double calculateTotalWorkHours(String firstName, String lastName, LocalDate startDate, LocalDate endDate);
}
