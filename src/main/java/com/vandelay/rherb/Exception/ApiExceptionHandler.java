package com.vandelay.rherb.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SuppressWarnings("unchecked")
@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler({ ItemNotFoundException.class })
    public ResponseEntity handleApiExceptions(ItemNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity handleOtherErrors(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
