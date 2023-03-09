package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/films", produces = MediaType.APPLICATION_JSON_VALUE)
public class FilmController {

    @Autowired
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        return filmService.getStorage().add(film);
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        return filmService.getStorage().update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void makeLike(@PathVariable("id") int filmId, @PathVariable int userId) {
        filmService.makeLike(filmId,userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") int filmId, @PathVariable int userId) {
        filmService.removeLike(filmId,userId);
    }

    @GetMapping("/popular")
    public List<Film> findTopFilmsByLikes(@RequestParam(defaultValue = "10") int count) {
        return filmService.findTopFilmsByLikes(count);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getStorage().getAll();
    }

}
