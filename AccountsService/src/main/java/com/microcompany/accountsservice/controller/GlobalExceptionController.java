package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.exception.GlobalException;
import com.microcompany.accountsservice.exception.OwnerNotFoundException;
import com.microcompany.accountsservice.model.StatusMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(value = GlobalException.class)
    public ResponseEntity handleGlobalException(GlobalException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new StatusMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(value = AccountNotfoundException.class)
    public ResponseEntity handleAccountNotFoundException(AccountNotfoundException ex) {
        return ResponseEntity.status(404).body(new StatusMessage(404, ex.getMessage()));
    }

    @ExceptionHandler(value = OwnerNotFoundException.class)
    public ResponseEntity handleOwnerNotFoundException(OwnerNotFoundException ex) {
        return ResponseEntity.status(404).body(new StatusMessage(404, ex.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMismatchEx(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new StatusMessage(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
