package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Set;

public interface LikeStorage {
    void saveAll(Film film);

    void updateAll(Film film);

    Set<Integer> load(int filmId);
}
