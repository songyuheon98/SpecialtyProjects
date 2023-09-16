package com.fanplayground.fanplayground.exception;

import com.fanplayground.fanplayground.dto.MessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DuplicateUsernameException.class})
    public ResponseEntity<MessageDto> handleException(DuplicateUsernameException ex) {
        MessageDto restApiException = new MessageDto(ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({FormNotMatchException.class})
    public ResponseEntity<MessageDto> handleException(FormNotMatchException ex) {
        MessageDto restApiException = new MessageDto(ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({TokenNotValidException.class})
    public ResponseEntity<MessageDto> handleException(TokenNotValidException ex) {
        MessageDto restApiException = new MessageDto(ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<MessageDto> handleException(IllegalArgumentException ex) {
        MessageDto restApiException = new MessageDto(ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({WriterNotMatchException.class})
    public ResponseEntity<MessageDto> handleException(WriterNotMatchException ex) {
        MessageDto restApiException = new MessageDto(ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}