package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.other.TestTools;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;


class FilmServiceTest {

    private UserService userService = TestTools.getUserService();
    private FilmService filmService = TestTools.getFilmService(userService.getStorage());


    @BeforeEach
    void initial() {
        UserStorage userStorage = userService.getStorage();
        userStorage.deleteAll();
        FilmStorage filmStorage = filmService.getStorage();
        filmStorage.deleteAll();
        for (int i = 1; i < 21; i++) {
            filmStorage.add(new Film(i,"film "+i,"", LocalDate.of(2020,4,i),i));
            userStorage.add(new User(i,"mail"+i+"@mail.ru","user"+i,"name"+i,LocalDate.of(1985,3,i)));
        }
    }

    @Test
    void shouldReturnTwoLikesWhenMakeTwoLikes() {
        int filmId = 1;
        int userId1 = 1;
        int userId2 = 2;
        Film film = filmService.getStorage().getFilmById(filmId);
        filmService.makeLike(filmId,userId1);
        filmService.makeLike(filmId,userId2);
        Assertions.assertEquals(2,film.getLikes().size(),"Неверное количество лайков");
    }

    @Test
    void shouldReturnOneLikeWhenRemoveOneLikeOfTwoLikes() {
        int filmId = 1;
        int userId1 = 1;
        int userId2 = 2;
        filmService.makeLike(filmId,userId1);
        filmService.makeLike(filmId,userId2);
        filmService.removeLike(filmId,userId1);
        Film film = filmService.getStorage().getFilmById(1);
        Assertions.assertEquals(1,film.getLikes().size(),"Неверное количество лайков");
    }

    @Test
    void shouldReturnZeroLikeWhenRemoveTwoLikeOfTwoLikes() {
        int filmId = 1;
        int userId1 = 1;
        int userId2 = 2;
        filmService.makeLike(filmId,userId1);
        filmService.makeLike(filmId,userId2);
        filmService.removeLike(filmId,userId1);
        filmService.removeLike(filmId,userId2);
        Film film = filmService.getStorage().getFilmById(1);
        Assertions.assertEquals(0,film.getLikes().size(),"Неверное количество лайков");
    }

    @Test
    void shouldReturnTopFilmsByLikes() {
        for (int filmId = 1; filmId < 21;filmId++) {
            for (int userId = 1; userId <= filmId;userId++) {
                filmService.makeLike(filmId,userId);
            }
        }
        List<Film> filmsTop = filmService.findTopFilmsByLikes(10);
        for (int i = 0; i < 10;i++) {
            Film film = filmsTop.get(i);
            Assertions.assertEquals(film.getLikes().size(),20-i,"Неправильный вывод топа фильмов");
        }
    }
}