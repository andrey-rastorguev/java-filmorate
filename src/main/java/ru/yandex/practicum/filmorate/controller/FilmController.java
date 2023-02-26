package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.other.UpdateException;
import ru.yandex.practicum.filmorate.other.ValidationException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private List<Film> films = new ArrayList<>();

    @PostMapping
    public Film create(@RequestBody @Valid Film film) throws ValidationException {
        film.valid();
        films.add(film);
        Film.incrementNextId();
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) throws ValidationException, UpdateException {
        film.valid();
        if (films.contains(film)) {
            films.remove(film);
            films.add(film);
        } else {
            throw new UpdateException("Film: Не найден объект для обновления");
        }
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return films;
    }

}
