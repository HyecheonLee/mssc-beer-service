package com.hyecheon.msscbeerservice.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MvcExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<List> validationErrorHandler(ConstraintViolationException ex) {
    final var errorList = ex.getConstraintViolations()
        .stream()
        .map(Object::toString)
        .collect(Collectors.toList());
    return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
  }
}
