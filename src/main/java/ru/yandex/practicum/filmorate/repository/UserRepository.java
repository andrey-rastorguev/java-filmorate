package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.other.UpdateException;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {
    private List<User> users = new ArrayList<>();
    private int nextId = 1;

    public void add(User user) {
        user.setId(nextId++);
        users.add(user);
    }

    public void update(User user) throws UpdateException {
        if (users.contains(user)) {
            users.remove(user);
            users.add(user);
        } else {
            throw new UpdateException("User: Не найден объект для обновления");
        }
    }

    public List<User> getAll() {
        return users;
    }
}
