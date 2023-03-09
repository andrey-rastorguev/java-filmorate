package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.other.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private List<User> users = new ArrayList<>();
    private int nextId = 1;

    @Override
    public User add(User user) {
        user.setId(nextId++);
        users.add(user);
        return user;
    }
    @Override
    public User delete(User user) {
        users.remove(user);
        return user;
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public User update(User user) {
        if (isExists(user)) {
            users.remove(user);
            users.add(user);
            return user;
        } else {
            throw new ObjectNotFoundException("User");
        }
    }
    @Override
    public boolean isExists(User user) {
        return users.contains(user);
    }

    @Override
    public User getUserById(int userId) {
        return users.stream().filter(u -> u.getId() == userId).findFirst().orElseThrow(() -> new ObjectNotFoundException("User"));
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}