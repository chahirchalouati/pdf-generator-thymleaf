package com.crcl.pdf.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<?> entityNotFound(EntityNotFoundException ex) {
        log.debug("handling exception::" + ex);
        return ResponseEntity.notFound().build();
    }
}
