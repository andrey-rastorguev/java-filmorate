package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User add(User user);
    User update(User user);
    User delete(User user);
    void deleteAll();
    boolean isExists(User user);
    User getUserById(int userId);
    List<User> getAll();
}
