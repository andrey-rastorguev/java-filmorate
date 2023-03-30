package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MpaServiceTest {

    @Autowired
    private MpaService mpaService;

    @Test
    void shouldGetFiveMpaWhenGetAll() {
        assertEquals(5, mpaService.getAll().size(), "Неверное количество вариантов рейтинга");
    }

    @Test
    void shouldGetTrueMpaWhenGetMpaById() {
        assertEquals("PG-13", mpaService.getMpaById(3).getName(), "Возвращен не верный рейтинг");
    }
}