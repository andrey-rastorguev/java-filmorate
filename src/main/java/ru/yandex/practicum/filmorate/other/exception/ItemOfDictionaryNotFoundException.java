package ru.yandex.practicum.filmorate.other.exception;

public class ItemOfDictionaryNotFoundException extends RuntimeException {
    public ItemOfDictionaryNotFoundException(String dictionaryName) {
        super("Item of dictionary '" + dictionaryName + "' not found");
    }
}
