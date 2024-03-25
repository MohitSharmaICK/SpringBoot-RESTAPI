package com.example.EducationalREST.Exceptions;

import org.springframework.http.HttpStatus;

public class RoutineNotValidException extends CustomBaseException{
    public RoutineNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST,new SimpleResponse(message));
    }
}
