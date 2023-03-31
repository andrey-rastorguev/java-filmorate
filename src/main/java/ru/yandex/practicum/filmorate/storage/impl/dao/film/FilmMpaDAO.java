package ru.yandex.practicum.filmorate.storage.impl.dao.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.records.MpaRecord;
import ru.yandex.practicum.filmorate.other.exception.ItemOfDictionaryNotFoundException;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Component
public class FilmMpaDAO implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmMpaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MpaRecord> getAll() {
        String sql = "SELECT mpa_rating_id, mpa_rating_code FROM mpa_rating";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));
    }

    public MpaRecord getMpaById(int idMPA) {
        String sql = "SELECT mpa_rating_id, mpa_rating_code FROM mpa_rating WHERE mpa_rating_id = ?";
        MpaRecord record = jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs), idMPA).stream().findFirst().orElse(null);
        if (record != null) {
            return record;
        } else {
            throw new ItemOfDictionaryNotFoundException("MPA");
        }
    }

    public MpaRecord getMpaByName(String codeMPA) {
        String sql = "SELECT mpa_rating_id, mpa_rating_code FROM mpa_rating WHERE mpa_rating_code = ?";
        MpaRecord record = jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs), codeMPA).stream().findFirst().orElse(null);
        if (record != null) {
            return record;
        } else {
            throw new ItemOfDictionaryNotFoundException("MPA");
        }
    }

    private MpaRecord makeMpa(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("mpa_rating_id");
        String code = rs.getString("mpa_rating_code");
        return new MpaRecord(id, code);
    }

}
