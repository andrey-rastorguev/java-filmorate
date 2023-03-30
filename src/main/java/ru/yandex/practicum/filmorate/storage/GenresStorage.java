package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.records.GenreRecord;

import java.util.List;
import java.util.Set;

public interface GenresStorage {
    List<GenreRecord> getAll();
    GenreRecord getGenreById(int idGenre);
    GenreRecord getGenreByName(String nameGenre);
    void saveAll(Film film);
    void updateAll(Film film);
    Set<GenreRecord> load(int filmId);
}
