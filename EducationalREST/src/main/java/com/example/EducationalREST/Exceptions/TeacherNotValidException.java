package com.example.EducationalREST.Exceptions;

import org.springframework.http.HttpStatus;

public class TeacherNotValidException extends CustomBaseException{
    public TeacherNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST,new SimpleResponse(message));
    }
}
