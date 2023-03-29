package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface FriendshipStorage {
    void saveAll(User user);
    void updateAll(User user);
    Map<Integer, Boolean> load(int userId);
}
