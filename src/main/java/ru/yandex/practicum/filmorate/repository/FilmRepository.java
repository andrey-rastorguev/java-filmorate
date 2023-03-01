package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.other.UpdateException;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilmRepository {
    private List<Film> films = new ArrayList<>();
    private int nextId = 1;

    public void add(Film film) {
        film.setId(nextId++);
        films.add(film);
    }

    public void update(Film film) throws UpdateException {
        if (films.contains(film)) {
            films.remove(film);
            films.add(film);
        } else {
            throw new UpdateException("Film: Не найден объект для обновления");
        }
    }

    public List<Film> getAll() {
        return films;
    }
}
