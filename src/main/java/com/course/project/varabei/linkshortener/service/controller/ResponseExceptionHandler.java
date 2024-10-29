package com.course.project.varabei.linkshortener.service.controller;

import com.course.project.varabei.linkshortener.service.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
    }
}
