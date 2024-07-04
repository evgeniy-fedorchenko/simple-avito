package com.evgeniyfedorchenko.simpleavito.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.awt.image.ImagingOpException;

@Slf4j
@RestControllerAdvice
public class SimpleAvitoExceptionHandler {

    @ExceptionHandler(ImagingOpException.class)
    public ResponseEntity<String> handleImagingOpException(ImagingOpException ex) {
        log.trace("ImagingOpException was thrown. Handle and suppress. Ex:", ex);
        return ResponseEntity.badRequest().body("Cannot processing. Ex:" + ex.getMessage());
    }
}
