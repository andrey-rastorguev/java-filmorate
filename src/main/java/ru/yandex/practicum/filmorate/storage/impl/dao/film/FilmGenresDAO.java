package ru.yandex.practicum.filmorate.storage.impl.dao.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.records.GenreRecord;
import ru.yandex.practicum.filmorate.other.exception.ItemOfDictionaryNotFoundException;
import ru.yandex.practicum.filmorate.storage.GenresStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmGenresDAO implements GenresStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmGenresDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GenreRecord> getAll() {
        String sql = "SELECT genre_id, genre_name FROM genres";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    public GenreRecord getGenreById(int idGenre) {
        String sql = "SELECT genre_id, genre_name FROM genres WHERE genre_id = ?";
        GenreRecord record = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), idGenre).stream().findFirst().orElse(null);
        if (record != null) {
            return record;
        } else {
            throw new ItemOfDictionaryNotFoundException("Genre");
        }
    }

    public GenreRecord getGenreByName(String nameGenre) {
        String sql = "SELECT genre_id, genre_name FROM genres WHERE genre_name = ?";
        GenreRecord record = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), nameGenre).stream().findFirst().orElse(null);
        if (record != null) {
            return record;
        } else {
            throw new ItemOfDictionaryNotFoundException("Genre");
        }
    }

    public void saveAll(Film film) {
        List<GenreRecord> genres = film.getGenres();
        for (GenreRecord genre : genres) {
            insertFilmGenre(film.getId(), genre);
        }
    }

    public void updateAll(Film film) {
        Set<GenreRecord> dbGenres = load(film.getId());
        Set<GenreRecord> genres = film.createSetGenres();
        for (GenreRecord genre : genres) {
            if (!dbGenres.contains(genre)) {
                insertFilmGenre(film.getId(), genre);
            }
        }
        for (GenreRecord dbGenre : dbGenres) {
            if (!genres.contains(dbGenre)) {
                deleteFilmGenre(film.getId(), dbGenre);
            }
        }
    }

    public Set<GenreRecord> load(int filmId) {
        String sql = "SELECT g.genre_name, g.genre_id FROM film_genre fg LEFT JOIN genres g ON fg.genre_id = g.genre_id WHERE fg.film_id = ?;";
        List<GenreRecord> genres = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), filmId);
        if (genres == null) {
            return new HashSet<>();
        } else {
            return genres.stream().collect(Collectors.toSet());
        }
    }

    private void insertFilmGenre(int filmId, GenreRecord genre) {
        String sql = "INSERT INTO film_genre (film_genre_id, film_id, genre_id) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql, getLastFilmGenreId() + 1, filmId, genre.getId());
    }

    private void deleteFilmGenre(int filmId, GenreRecord genre) {
        String sql = "DELETE FROM film_genre WHERE film_id = ? and genre_id = ?;";
        jdbcTemplate.update(sql, filmId, genre.getId());
    }

    private GenreRecord makeGenre(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("genre_id");
        String name = rs.getString("genre_name");
        return new GenreRecord(id, name);
    }

    private int getLastFilmGenreId() {
        String sql = "SELECT * FROM film_genre ORDER BY film_genre_id DESC LIMIT 1";
        List<Integer> ids = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("film_genre_id"));
        if (ids.size() == 0) {
            return 0;
        } else {
            return ids.get(0);
        }
    }

}
