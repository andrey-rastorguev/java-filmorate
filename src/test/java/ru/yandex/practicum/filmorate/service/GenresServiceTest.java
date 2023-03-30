package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GenresServiceTest {

    @Autowired
    private GenresService genresService;

    @Test
    void shouldReturnSixGenres() {
        assertEquals(6, genresService.getAll().size(), "Неверное количество жанров");
    }

    @Test
    void shouldReturnTrueGenreById() {
        assertEquals("Документальный", genresService.getGenreById(5).getName(), "Возвращен не верный жанр");
    }
}