package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FilmServiceTest {

    @Autowired
    private FilmService filmService;

    @Test
    public void shouldReturnFourFilms() {
        assertEquals(4, filmService.getAllFilms().size(), "Недопустимое количество фильмов");
    }

    @Test
    public void shouldReturnSecondFilm() {
        assertEquals("Дом в огне", filmService.getFilmById(2).getName(), "Возвращен не верный фильм");
    }

    @Test
    public void shouldReturnSecondFilmIsTop() {
        assertEquals(2, filmService.findTopFilmsByLikes(10).get(0).getId(), "Не верный топ фильмов");
    }

}