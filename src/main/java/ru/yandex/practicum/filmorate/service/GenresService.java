package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.GenreRecord;
import ru.yandex.practicum.filmorate.storage.GenresStorage;

import java.util.List;

@Service
public class GenresService {
    @Autowired
    private GenresStorage genreStorage;

    public List<GenreRecord> getAll() {
        return genreStorage.getAll();
    }

    public GenreRecord getGenreById(int genreId) {
        return genreStorage.getGenreById(genreId);
    }
}
