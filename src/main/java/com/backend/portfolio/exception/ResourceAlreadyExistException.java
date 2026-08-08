package com.backend.portfolio.exception;

public class ResourceAlreadyExistException extends RuntimeException {

    public ResourceAlreadyExistException (String message){
        super(message);
    }

}
