package com.Virtual_Bank_System.Transaction.Service.Exception;

import com.Virtual_Bank_System.Transaction.Service.DTOs.ErrorResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)

    public ResponseEntity<ErrorResponseDto>handleException(BaseException ex){
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getStatus().value(),
                ex.getStatus().getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
