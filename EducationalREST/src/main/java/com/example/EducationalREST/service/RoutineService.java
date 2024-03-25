package com.example.EducationalREST.service;

import com.example.EducationalREST.dto.RoutineCreationRequest;
import com.example.EducationalREST.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.swing.text.html.parser.Entity;

public interface RoutineService  {

    ResponseEntity addRoutine(RoutineCreationRequest routineCreationRequest);



}
