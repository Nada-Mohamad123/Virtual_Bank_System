package com.Virtual_Bank_System.Transaction.Service.Exception;

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
    public static class transferErrorException extends BaseException{
        public transferErrorException(String message){
            super(message,HttpStatus.BAD_REQUEST);
        }
    }
    public static class transactionHistoryException extends BaseException{
        public transactionHistoryException(String message){
            super(message,HttpStatus.NOT_FOUND);
        }
    }
}
