package com.task.theeducationalinstitute.exception;

public class GroupNotFoundException extends RuntimeException{
    public GroupNotFoundException(String message)
    {
        super(message);
    }
}
