package com.btbatux.taskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler, uygulama genelinde meydana gelen istisnaları ele alan küresel hata işleyicidir.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ResourceNotFoundException istisnasını ele alır ve 404 Not Found yanıtı döner.
     * @param ex ResourceNotFoundException
     * @return ResponseEntity<String> - hata mesajı ile birlikte 404 Not Found yanıtı
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }


}
