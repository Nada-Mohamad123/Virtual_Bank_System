package com.Virtual_Bank_System.User_Service.Exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{
    private final HttpStatus status;

    public BaseException(String message,HttpStatus status){
        super(message);
        this.status = status;

    }

    public HttpStatus getStatus() {
        return status;
    }


   public static class UserExistsException extends BaseException {
        public UserExistsException(String Message) {
            super(Message,HttpStatus.CONFLICT);
        }
    }

    public static class InvalidCredentialsException extends BaseException{
        public InvalidCredentialsException(String Message){
            super(Message,HttpStatus.UNAUTHORIZED);
        }
    }

    public static class UserNotFoundException extends BaseException {
        public UserNotFoundException(String Message){
            super(Message,HttpStatus.NOT_FOUND);
        }
    }
}
