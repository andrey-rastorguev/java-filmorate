package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
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
        user1.addFriend(userId2);
        user2.addFriend(userId1);
        storage.update(user1);
        storage.update(user2);
    }

    public User removeFriendForUser(int userId, int friendId) {
        User user = storage.getUserById(userId);
        User friend = storage.getUserById(friendId);
        user.removeFriend(friendId);
        return storage.update(user);
    }

    public List<User> findFriends(int userId) {
        User user = storage.getUserById(userId);
        return getUsersFromListId(user.getAllFriends());
    }

    public List<User> findMutualFriends(int userId1, int userId2) {
        User user1 = storage.getUserById(userId1);
        User user2 = storage.getUserById(userId2);
        Set<Integer> mutualFriends = new HashSet<>();
        for (Integer friendId : user1.getAllFriends()) {
            if (user2.getAllFriends().contains(friendId)) {
                mutualFriends.add(friendId);
            }
        }
        return getUsersFromListId(mutualFriends.stream().collect(Collectors.toList()));
    }

    private List<User> getUsersFromListId(List<Integer> setUsersId) {
        List<User> users = new ArrayList<>();
        for (Integer userId: setUsersId) {
            users.add(storage.getUserById(userId));
        }
        return users;
    }

    public User addUser (User user) {
        return storage.add(user);
    }

    public User updateUser (User user) {
        return storage.update(user);
    }

    public User getUserById(int idUser) {
        return storage.getUserById(idUser);
    }

    public void deleteAllUsers() {
        storage.deleteAll();
    }
}
