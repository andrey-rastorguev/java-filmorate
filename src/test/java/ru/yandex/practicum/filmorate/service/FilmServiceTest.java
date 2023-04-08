package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FilmServiceTest {

    @Autowired
    private FilmService filmService;

    @Autowired
    private GenresService genresService;

    @Autowired
    private MpaService mpaService;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void initial() {
        userService.deleteAllUsers();
        filmService.deleteAllFilms();
        User user;
        for (int i = 1; i < 7; i++) {
            user = User.builder()
                    .email("e" + i + "@yandex.ru")
                    .login("login " + i)
                    .name("name " + i)
                    .birthday(LocalDate.of(1990 + 3 * i, 2 + i, 25 - 2 * i))
                    .build();
            userService.addUser(user);
        }
        userService.makeFriends(1, 3);
        userService.makeFriends(3, 5);
        user = userService.getUserById(5);
        user.acceptFriend(3);
        userService.updateUser(user);
        Film film;
        for (int i = 1; i < 6; i++) {
            film = Film.builder()
                    .name("Фильм " + i)
                    .description("Фильм об " + i)
                    .mpa(mpaService.getMpaById(i))
                    .releaseDate(LocalDate.of(1980 + 3 * i, 3 + i, 13 + 2 * i))
                    .duration(20 * i)
                    .build();
            film.addGenres(genresService.getGenreById(i));
            filmService.addFilm(film);
        }
        filmService.makeLike(2, 4);
        filmService.makeLike(2, 6);
        filmService.makeLike(3, 5);
    }

    @Test
    public void shouldReturnFiveFilms() {
        assertEquals(5, filmService.getAllFilms().size(), "Недопустимое количество фильмов");
    }

    @Test
    public void shouldReturnSixLikesForSecondFilm() {
        filmService.makeLike(2, 1);
        filmService.makeLike(2, 2);
        filmService.makeLike(2, 3);
        filmService.makeLike(2, 4);
        filmService.makeLike(2, 5);
        assertEquals(6, filmService.getFilmById(2).getLikes().size(), "Недопустимое лайков для фильма");
    }


    @Test
    public void shouldReturnZeroLikesForSecondFilm() {
        filmService.removeLike(2, 4);
        filmService.removeLike(2, 6);
        assertEquals(0, filmService.getFilmById(2).getLikes().size(), "Недопустимое лайков для фильма");
    }

    @Test
    public void shouldReturnSecondFilm() {
        assertEquals("Фильм 2", filmService.getFilmById(2).getName(), "Возвращен не верный фильм");
    }

    @Test
    public void shouldReturnSecondFilmIsTop() {
        assertEquals(2, filmService.findTopFilmsByLikes(10).get(0).getId(), "Не верный топ фильмов");
    }

}