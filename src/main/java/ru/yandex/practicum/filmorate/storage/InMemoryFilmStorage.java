package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.other.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private List<Film> films = new ArrayList<>();
    private int nextId = 1;

    @Override
    public Film add(Film film) {
        film.setId(nextId++);
        films.add(film);
        return film;
    }

    @Override
    public Film delete(Film film) {
        films.remove(film);
        return film;
    }

    @Override
    public void deleteAll() {
        films.clear();
    }

    @Override
    public Film update(Film film) {
        if (films.contains(film)) {
            films.remove(film);
            films.add(film);
            return film;
        } else {
            throw new ObjectNotFoundException("Film");
        }
    }

    @Override
    public boolean isExists(Film film) {
        return films.contains(film);
    }

    @Override
    public Film getFilmById(int filmId) {
        return films.stream().filter(f -> f.getId() == filmId).findFirst().orElseThrow(() -> new ObjectNotFoundException("Film"));
    }

    @Override
    public List<Film> getAll() {
        return films;
    }
}
