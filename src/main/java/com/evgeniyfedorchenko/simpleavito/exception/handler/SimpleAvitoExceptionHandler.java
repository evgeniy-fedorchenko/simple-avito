package com.evgeniyfedorchenko.simpleavito.exception.handler;

import com.evgeniyfedorchenko.simpleavito.exception.ImageParsedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class SimpleAvitoExceptionHandler {

    //    Ошибка при обработке переданного изображения
    @ExceptionHandler(ImageParsedException.class)
    public ResponseEntity<ErrorResponse> handleImageParsedException(ImageParsedException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(1)
                .errorsCount(1)
                .details(ex.getMessage())
                .build();
        log.trace("{} was thrown. Handle and suppress. Ex:", ex.getClass().getSimpleName(), ex);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    //    Проваленная валидация параметров в контроллере
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> details = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null
                                ? fieldError.getDefaultMessage()
                                : fieldError.getField()));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(4)
                .errorsCount(details.size())
                .detailsMap(details)
                .badObjName(ex.getBindingResult().getObjectName())
                .badObj(ex.getBindingResult().getTarget())
                .build();

        log.trace("MethodArgumentNotValidException was thrown. Handle and suppress. Ex:", ex);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Void> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}


