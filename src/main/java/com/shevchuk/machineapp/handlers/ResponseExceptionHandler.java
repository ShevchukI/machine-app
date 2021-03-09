package com.shevchuk.machineapp.handlers;

import com.shevchuk.machineapp.exceptions.FileException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FileException.class)
    public ResponseEntity<String> handleFileException(FileException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        super.handleMethodArgumentNotValid(ex, headers, status, request);

        List<ObjectError> fieldErrors = ex.getBindingResult().getAllErrors();
        List<String> errorMessages = fieldErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}