package ru.yandex.practicum.filmorate.storage.impl.dao.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.other.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenresStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("dbFilmStorage")
public class FilmDAO implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    private final LikeStorage likeStorage;

    private final MpaStorage mpaStorage;

    private final GenresStorage genreStorage;

    public FilmDAO(JdbcTemplate jdbcTemplate, LikeStorage likeStorage, MpaStorage mpaStorage, GenresStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.likeStorage = likeStorage;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
    }

    @Override
    public Film add(Film film) {
        film = insertFilm(film);
        likeStorage.saveAll(film);
        genreStorage.saveAll(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        film = updateFilm(film);
        likeStorage.updateAll(film);
        genreStorage.updateAll(film);
        return film;
    }

    @Override
    public Film delete(Film film) {
        return deleteFilm(film);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from films";
        jdbcTemplate.update(sql);
    }

    @Override
    public boolean isExists(Film film) {
        String sql = "SELECT * FROM films WHERE film_id = ?";
        return !jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("film_id"), film.getId()).isEmpty();
    }

    @Override
    public Film getFilmById(int filmId) {
        String sql = "SELECT * FROM films WHERE film_id = ?";
        Film film = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), filmId).stream().findFirst().orElse(null);
        if (film != null) {
            return film;
        } else {
            throw new ObjectNotFoundException("Film");
        }
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT * FROM films ORDER BY film_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    private Film insertFilm(Film film) {
        String sqlUser = "INSERT INTO films (film_id, film_name, description, mpa_rating_id, release_date, duration) VALUES (?, ?, ?, ?, ?, ?);";
        film.setId(getLastFilmId() + 1);
        jdbcTemplate.update(sqlUser,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                film.getReleaseDate(),
                film.getDuration());
        return film;
    }

    private Film updateFilm(Film film) {
        if (isExists(film)) {
            String sql = "UPDATE films SET film_name = ?, description = ?, mpa_rating_id = ?, release_date = ?, duration = ? WHERE film_id = ?;";
            jdbcTemplate.update(sql,
                    film.getName(),
                    film.getDescription(),
                    film.getMpa().getId(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getId());
            return film;
        } else {
            throw new ObjectNotFoundException("Film");
        }
    }

    private Film deleteFilm(Film film) {
        String sql = "delete from films where film_id = ?";
        if (jdbcTemplate.update(sql, film.getId()) > 0) {
            return film;
        } else {
            throw new ObjectNotFoundException("Film");
        }
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("film_id");
        Film film = Film.builder()
                .id(id)
                .name(rs.getString("film_name"))
                .description(rs.getString("description"))
                .mpa(mpaStorage.getMpaById(rs.getInt("mpa_rating_id")))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .genres(genreStorage.load(id))
                .likes(likeStorage.load(id))
                .build();

        return film;
    }

    private int getLastFilmId() {
        String sql = "SELECT * FROM films ORDER BY film_id DESC LIMIT 1";
        List<Integer> ids = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("film_id"));
        if (ids.size() == 0) {
            return 0;
        } else {
            return ids.get(0);
        }
    }

}
