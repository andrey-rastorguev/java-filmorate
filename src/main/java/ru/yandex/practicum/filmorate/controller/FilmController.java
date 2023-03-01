package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.other.UpdateException;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        filmRepository.add(film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) throws UpdateException {
        filmRepository.update(film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmRepository.getAll();
    }

}
