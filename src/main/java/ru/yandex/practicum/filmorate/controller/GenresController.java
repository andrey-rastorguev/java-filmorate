package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.records.GenreRecord;
import ru.yandex.practicum.filmorate.service.GenresService;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenresController {
    @Autowired
    private final GenresService genresService;

    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

    @GetMapping
    public List<GenreRecord> getAll() {
        return genresService.getAll();
    }

    @GetMapping("/{id}")
    public GenreRecord getGenreById(@PathVariable("id") int genreId) {
        return genresService.getGenreById(genreId);
    }
}
