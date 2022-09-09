package backend.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import backend.exception.BackendException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValid(
            HttpServletResponse response, MethodArgumentNotValidException ex) throws IOException {
        final String message = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ((FieldError) error).getDefaultMessage() + " (" + ((FieldError) error).getField() + ")")
                .reduce("", (s1, s2) -> (s1.isEmpty()) ? s2 : s1 + ", " + s2);

        final PrintWriter writer = response.getWriter();
        writer.print(message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleBackendException(EntityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingParams(MissingServletRequestParameterException e) {
        String name = e.getParameterName();
        return new ResponseEntity<>("Parameter is missing: " + name, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BackendException.class)
    public ResponseEntity<Object> handleBackendException(BackendException e) {

        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
