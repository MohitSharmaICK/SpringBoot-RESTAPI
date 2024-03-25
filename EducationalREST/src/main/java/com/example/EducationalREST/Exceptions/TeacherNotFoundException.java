package com.example.EducationalREST.Exceptions;

import org.springframework.http.HttpStatus;

public class TeacherNotFoundException extends CustomBaseException{
    public TeacherNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message));
    }
}
