package com.example.EducationalREST.Exceptions;

import org.springframework.http.HttpStatus;

public class GroupNotValidException extends CustomBaseException{
    public GroupNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST,new SimpleResponse(message));
    }
}
