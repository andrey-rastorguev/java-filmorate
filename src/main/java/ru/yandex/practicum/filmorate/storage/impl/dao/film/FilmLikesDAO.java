package ru.yandex.practicum.filmorate.storage.impl.dao.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmLikesDAO implements LikeStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public FilmLikesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAll(Film film) {
        List<Integer> likes = film.getLikes();
        for (Integer userId : likes) {
            insertLike(film.getId(), userId);
        }
    }

    public void updateAll(Film film) {
        Set<Integer> dbLikes = load(film.getId());
        List<Integer> likes = film.getLikes();
        for (int userId : likes) {
            if (!dbLikes.contains(userId)) {
                insertLike(film.getId(), userId);
            }
        }
        for (int dbUserId : dbLikes) {
            if (!likes.contains(dbUserId)) {
                remove(film.getId(), dbUserId);
            }
        }
    }

    public Set<Integer> load(int filmId) {
        String sql = "SELECT user_id FROM likes WHERE film_id = ?;";
        List<Integer> likes = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id"), filmId);
        if (likes == null) {
            return new HashSet<>();
        } else {
            return likes.stream().collect(Collectors.toSet());
        }
    }

    private void remove(int filmId, int userId) {
        String sql = "DELETE FROM likes WHERE film_id = ? and user_id = ?;";
        jdbcTemplate.update(sql, filmId, userId);
    }

    private void insertLike(int filmId, int userId) {
        String sql = "INSERT INTO likes (like_id, film_id, user_id) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql, getLastLikeId() + 1, filmId, userId);
    }

    private int getLastLikeId() {
        String sql = "SELECT * FROM likes ORDER BY like_id DESC LIMIT 1";
        List<Integer> ids = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("like_id"));
        if (ids.size() == 0) {
            return 0;
        } else {
            return ids.get(0);
        }
    }

}
