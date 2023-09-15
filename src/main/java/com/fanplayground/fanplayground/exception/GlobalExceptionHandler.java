package com.fanplayground.fanplayground.exception;

import com.fanplayground.fanplayground.entity.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DuplicateUsernameException.class})
    public ResponseEntity<Message> handleException(DuplicateUsernameException ex) {
        Message restApiException = new Message(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({FormNotMatchException.class})
    public ResponseEntity<Message> handleException(FormNotMatchException ex) {
        Message restApiException = new Message(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({TokenNotValidException.class})
    public ResponseEntity<Message> handleException(TokenNotValidException ex) {
        Message restApiException = new Message(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Message> handleException(IllegalArgumentException ex) {
        Message restApiException = new Message(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({WriterNotMatchException.class})
    public ResponseEntity<Message> handleException(WriterNotMatchException ex) {
        Message restApiException = new Message(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}