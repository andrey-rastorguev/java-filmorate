package ru.yandex.practicum.filmorate.other.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String objectName) {
        super("'" + objectName + "' not found");
    }
}
