package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film add(Film film);

    Film update(Film film);

    Film delete(Film film);

    void deleteAll();

    boolean isExists(Film film);

    Film getFilmById(int filmId);

    List<Film> getAll();
}
