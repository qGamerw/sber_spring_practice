package ru.sber.controllerAdvice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sber.model.ErrorDetails;

/**
 * Перехватываем исключения во время выполнения запросов
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> exceptionRuntimeHandler(RuntimeException exception) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(exception.getMessage());

        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }
}
