package com.example.EducationalREST.controller;


import com.example.EducationalREST.Exceptions.RoutineNotValidException;
import com.example.EducationalREST.dto.RoutineCreationRequest;
import com.example.EducationalREST.entity.Routine;
import com.example.EducationalREST.repository.RoutineRepository;
import com.example.EducationalREST.service.RoutineServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/routine")
public class RoutineController {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private RoutineServiceImplementation routineServiceImplementation;

    @PostMapping("/create")
    public ResponseEntity addRoutine(@RequestBody RoutineCreationRequest routineCreationRequest)
    {
        try {
            routineServiceImplementation.addRoutine(routineCreationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Routine created successfully.");
        }catch (RoutineNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getSimpleResponse());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create routine.");
        }
    }
}
