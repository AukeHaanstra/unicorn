package nl.pancompany.unicorn.web.controller;

import nl.pancompany.unicorn.application.unicorn.service.UnicornNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnicornNotFoundException.class)
    public ResponseEntity<?> handleAccessDeniedException(Exception ex, WebRequest request) {
        return ResponseEntity.notFound().build();
    }
}
