package ru.yandex.practicum.filmorate.storage.impl.dao.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.other.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component("dbUserStorage")
public class UserDAO implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    private final FriendshipStorage friendshipStorage;

    public UserDAO(JdbcTemplate jdbcTemplate, FriendshipStorage friendshipStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendshipStorage = friendshipStorage;
    }

    @Override
    public User add(User user) {
        user = insertUser(user);
        friendshipStorage.saveAll(user);
        return user;
    }

    @Override
    public User update(User user) {
        user = updateUser(user);
        friendshipStorage.updateAll(user);
        return user;
    }

    @Override
    public User delete(User user) {
        return deleteUser(user);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from users";
        jdbcTemplate.update(sql);
    }

    @Override
    public boolean isExists(User user) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return !jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id"), user.getId()).isEmpty();
    }

    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        User user = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), userId).stream().findFirst().orElse(null);
        if (user != null) {
            return user;
        } else {
            throw new ObjectNotFoundException("User");
        }
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users ORDER BY user_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    private User makeUser(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("user_id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("user_name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        Map<Integer, Boolean> friends = friendshipStorage.load(id);
        User user = new User(id, email, login, name, birthday, friends);
        return user;
    }

    private int getLastUserId() {
        String sql = "SELECT * FROM users ORDER BY user_id DESC LIMIT 1";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id")).get(0);
    }

    private User insertUser(User user) {
        String sql = "INSERT INTO users (email, login, user_name, birthday) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        user.setId(getLastUserId());
        return user;
    }

    private User updateUser(User user) {
        if (isExists(user)) {
            String sql = "UPDATE users SET email = ?, login = ?, user_name = ?, birthday = ? WHERE user_id = ?;";
            jdbcTemplate.update(sql,
                    user.getEmail(),
                    user.getLogin(),
                    user.getName(),
                    user.getBirthday(),
                    user.getId());
            return user;
        } else {
            throw new ObjectNotFoundException("User");
        }
    }

    private User deleteUser(User user) {
        String sql = "delete from users where user_id = ?";
        if (jdbcTemplate.update(sql, user.getId()) > 0) {
            return user;
        } else {
            throw new ObjectNotFoundException("User");
        }
    }

}
