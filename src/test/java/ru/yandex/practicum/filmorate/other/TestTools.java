package ru.yandex.practicum.filmorate.other;

import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

public class TestTools {

    public static FilmStorage getFilmStorage() {
        return new InMemoryFilmStorage();
    }

    public static FilmService getFilmService(UserStorage userStorage) {
        return new FilmService(getFilmStorage(),userStorage);
    }

    public static UserStorage getUserStorage() {
        return new InMemoryUserStorage();
    }

    public static UserService getUserService() {
        return new UserService(getUserStorage());
    }

}
