package com.example.bankcp.validation;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;


@RestControllerAdvice
public class ValidationErrorHandle {
    record ValidationErrorResponse(String campo, String mensagem){
        public ValidationErrorResponse(FieldError fildError){
            this(fildError.getField(), fildError.getDefaultMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public List<ValidationErrorResponse> handle(MethodArgumentNotValidException exception){
        return exception
                        .getFieldErrors()
                        .stream()
                        .map(ValidationErrorResponse::new)
                        .toList();
    }
}
