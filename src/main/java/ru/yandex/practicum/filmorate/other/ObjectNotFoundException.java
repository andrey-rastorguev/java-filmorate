package ru.yandex.practicum.filmorate.other;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String objectName) {
        super("'" + objectName + "' не найден");
    }
}
