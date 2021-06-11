package com.example.authservice.exceptions;


import com.example.authservice.wrappers.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class , InvalidCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> errorHandler(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorMessage(exception.getMessage());

        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(errorResponse , HttpStatus.UNAUTHORIZED) ;
    }

}
