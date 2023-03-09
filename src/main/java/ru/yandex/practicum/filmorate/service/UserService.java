package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.other.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Service
public class UserService {

    @Autowired
    private final UserStorage storage;

    public UserService(UserStorage userStorage) {
        this.storage = userStorage;
    }

    public List<User> getAllUsers() {
        return storage.getAll();
    }


    public void makeFriends(int userId1, int userId2) {
        User user1 = storage.getUserById(userId1);
        User user2 = storage.getUserById(userId2);
        user1.getFriends().add(user2);
        user2.getFriends().add(user1);
        storage.update(user1);
        storage.update(user2);
    }

    public User removeFriendForUser(int userId, int friendId) {
        User user = storage.getUserById(userId);
        User friend = storage.getUserById(friendId);
        user.getFriends().remove(friend);
        return storage.update(user);
    }

    public List<User> findFriends(int userId) {
        User user = storage.getUserById(userId);
        return user.getFriends().stream().collect(Collectors.toList());
    }

    public List<User> findMutualFriends(int userId1, int userId2) {
        User user1 = storage.getUserById(userId1);
        User user2 = storage.getUserById(userId2);
        Set<User> mutualFriends = new HashSet<>();
        for (User friend : user1.getFriends()) {
            if (user2.getFriends().contains(friend)) {
                mutualFriends.add(friend);
            }
        }
        return mutualFriends.stream().collect(Collectors.toList());
    }

}
