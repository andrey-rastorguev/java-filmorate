package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.records.GenreRecord;
import ru.yandex.practicum.filmorate.storage.GenresStorage;

import java.util.List;

@Service
public class GenresService {

    private final GenresStorage genreStorage;

    public GenresService(GenresStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<GenreRecord> getAll() {
        return genreStorage.getAll();
    }

    public GenreRecord getGenreById(int genreId) {
        return genreStorage.getGenreById(genreId);
    }
}
