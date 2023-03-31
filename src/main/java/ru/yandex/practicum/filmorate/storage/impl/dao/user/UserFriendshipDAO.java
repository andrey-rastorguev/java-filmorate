package ru.yandex.practicum.filmorate.storage.impl.dao.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class UserFriendshipDAO implements FriendshipStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserFriendshipDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAll(User user) {
        Map<Integer, Boolean> friendships = user.allFriendships();
        for (Integer friendId : friendships.keySet()) {
            String sqlFriendship = "INSERT INTO friendships (friend_from_id, friend_to_id, is_accept) VALUES (?, ?, ?);";
            jdbcTemplate.update(sqlFriendship, user.getId(), friendId, friendships.get(friendId));
        }
    }

    public void updateAll(User user) {
        Map<Integer, Boolean> dbFriendships = load(user.getId());
        Map<Integer, Boolean> friendships = user.allFriendships();
        for (int friendId : friendships.keySet()) {
            if (!dbFriendships.containsKey(friendId)) {
                insertFriendship(user.getId(), friendId, friendships.get(friendId));
            }
        }
        for (int dbFriendId : dbFriendships.keySet()) {
            if (!friendships.containsKey(dbFriendId)) {
                remove(user.getId(), dbFriendId);
            } else if (friendships.get(dbFriendId) != dbFriendships.get(dbFriendId)) {
                updateFriendship(user.getId(), dbFriendId, friendships.get(dbFriendId));
            }
        }
    }

    public Map<Integer, Boolean> load(int userId) {
        String sql = "SELECT friend_to_id, is_accept FROM friendships WHERE friend_from_id = ?;";
        List<Integer> keys = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("friend_to_id"), userId)
                .stream().collect(Collectors.toList());
        List<Boolean> values = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getBoolean("is_accept"), userId)
                .stream().collect(Collectors.toList());
        return IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(keys::get, values::get));

    }

    private void remove(int userId, int friendId) {
        String sql = "DELETE FROM friendships WHERE friend_from_id = ? and friend_to_id = ?;";
        jdbcTemplate.update(sql, userId, friendId);
    }

    private void insertFriendship(int userId, int friendId, boolean isAccept) {
        String sql = "INSERT INTO friendships (friend_from_id, friend_to_id, is_accept) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql, userId, friendId, isAccept);
    }

    private void updateFriendship(int userId, int friendId, boolean isAccept) {
        String sql = "UPDATE friendships SET accept = ? WHERE friend_from_id = ? and friend_to_id = ?;";
        jdbcTemplate.update(sql, isAccept, userId, friendId);
    }

}
