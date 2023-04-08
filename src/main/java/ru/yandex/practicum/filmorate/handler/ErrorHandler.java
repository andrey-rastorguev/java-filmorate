package ru.yandex.practicum.filmorate.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.other.ErrorResponse;
import ru.yandex.practicum.filmorate.other.exception.ItemOfDictionaryNotFoundException;
import ru.yandex.practicum.filmorate.other.exception.ObjectNotFoundException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handlerObjectNotFound(final ObjectNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handlerItemOfDictionaryNotFound(final ItemOfDictionaryNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
