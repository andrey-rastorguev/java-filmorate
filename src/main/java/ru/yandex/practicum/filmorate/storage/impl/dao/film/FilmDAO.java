package ru.yandex.practicum.filmorate.storage.impl.dao.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.records.GenreRecord;
import ru.yandex.practicum.filmorate.model.records.MpaRecord;
import ru.yandex.practicum.filmorate.other.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenresStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("dbFilmStorage")
public class FilmDAO implements FilmStorage {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LikeStorage likeStorage;
    @Autowired
    private MpaStorage mpaStorage;
    @Autowired
    private GenresStorage genreStorage;

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
        String sqlUser = "INSERT INTO films (film_name, description, mpa_rating_id, release_date, duration) VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sqlUser,
                film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                film.getReleaseDate(),
                film.getDuration());
        film.setId(getLastFilmId());
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
        String name = rs.getString("film_name");
        String description = rs.getString("description");
        MpaRecord mpa = mpaStorage.getMpaById(rs.getInt("mpa_rating_id"));
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Integer duration = rs.getInt("duration");
        Set<Integer> likes = likeStorage.load(id);
        Set<GenreRecord> genres = genreStorage.load(id);
        Film film = new Film(id, name, description, mpa, releaseDate, duration, genres.stream().collect(Collectors.toList()), likes);
        return film;
    }

    private int getLastFilmId() {
        String sql = "SELECT * FROM films ORDER BY film_id DESC LIMIT 1";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("film_id")).get(0);
    }

}
