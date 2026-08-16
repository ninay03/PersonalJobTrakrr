package com.ninay.JobTrakrr.exception;

public class InvalidStatusTransitionException extends RuntimeException{

    public InvalidStatusTransitionException (String message){
        super(message);
    }

}
