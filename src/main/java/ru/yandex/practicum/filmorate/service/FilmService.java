package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilmService {

    @Autowired
    private final FilmStorage storage;
    @Autowired
    private final UserStorage storageUser;

    public FilmService(FilmStorage storage, UserStorage storageUser) {
        this.storage = storage;
        this.storageUser = storageUser;
    }

    public void makeLike(int filmId, int userId) {
        Film film = storage.getFilmById(filmId);
        User user = storageUser.getUserById(userId);
        film.getLikes().add(userId);
        storage.update(film);
    }

    public void removeLike(int filmId, int userId) {
        Film film = storage.getFilmById(filmId);
        User user = storageUser.getUserById(userId);
        film.getLikes().remove(userId);
        storage.update(film);
    }


    public List<Film> findTopFilmsByLikes(int sizeOfTop) {
        List<Film> topFilms = storage.getAll().stream().collect(Collectors.toList());
        Collections.sort(topFilms, Comparator.comparingInt(x -> -x.getLikes().size()));
        int currentSizeTop = sizeOfTop;
        if (!topFilms.isEmpty()) {
            if (topFilms.size() < sizeOfTop) {
                currentSizeTop = topFilms.size();
            }
            topFilms = topFilms.subList(0, currentSizeTop);
        }
        return topFilms;
    }

    public Film addFilm(Film film) {
        return storage.add(film);
    }

    public Film updateFilm(Film film) {
        return storage.update(film);
    }

    public Film getFilmById(int idFilm) {
        return storage.getFilmById(idFilm);
    }

    public List<Film> getAllFilms() {
        return storage.getAll();
    }

    public void deleteAllFilms() {
        storage.deleteAll();
    }
}
