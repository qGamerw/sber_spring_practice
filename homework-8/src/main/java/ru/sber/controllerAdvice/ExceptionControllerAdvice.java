package ru.sber.controllerAdvice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sber.exception.BankClientException;
import ru.sber.exception.EmptyBasketException;
import ru.sber.exception.IncorrectAmountException;
import ru.sber.exception.NotEnoughMoneyException;
import ru.sber.model.ErrorDetails;

/**
 * Перехватываем исключения во время выполнения запросов
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorDetails> exceptionNotEnoughMoneyHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("Недостаточно средств для оплаты");
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(EmptyBasketException.class)
    public ResponseEntity<ErrorDetails> exceptionEmptyBasketExceptionHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("Пустая корзина");
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(IncorrectAmountException.class)
    public ResponseEntity<ErrorDetails> exceptionIncorrectAmountHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("Некорректное значение количества продуктов");
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(BankClientException.class)
    public ResponseEntity<ErrorDetails> exceptionBankClientHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("Не является клиентом банка");
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }
}
