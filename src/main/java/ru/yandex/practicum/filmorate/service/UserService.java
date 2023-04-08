package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("dbUserStorage")
    private final UserStorage storage;

    public UserService(@Qualifier("dbUserStorage") UserStorage userStorage) {
        this.storage = userStorage;
    }

    public List<User> getAllUsers() {
        return storage.getAll();
    }


    public User makeFriends(int fromUserId, int toUserId) {
        User fromUser = storage.getUserById(fromUserId);
        User toUser = storage.getUserById(toUserId);
        fromUser.addFriend(toUserId);
        fromUser.acceptFriend(toUserId);
        toUser.addFriend(fromUserId);
        storage.update(fromUser);
        storage.update(toUser);
        return fromUser;
    }

    public User removeFriendForUser(int userId, int friendId) {
        User user = storage.getUserById(userId);
        User friend = storage.getUserById(friendId);
        user.removeFriend(friendId);
        friend.removeFriend(userId);
        storage.update(user);
        storage.update(friend);
        return user;
    }

    public List<User> findFriends(int userId) {
        User user = storage.getUserById(userId);
        return getUsersFromListId(user.getFriends());
    }

    public List<User> findMutualFriends(int userId1, int userId2) {
        User user1 = storage.getUserById(userId1);
        User user2 = storage.getUserById(userId2);
        Set<Integer> mutualFriends = new HashSet<>();
        for (Integer friendId : user1.getFriends()) {
            if (user2.getFriends().contains(friendId)) {
                mutualFriends.add(friendId);
            }
        }
        return getUsersFromListId(mutualFriends.stream().collect(Collectors.toList()));
    }

    private List<User> getUsersFromListId(List<Integer> setUsersId) {
        List<User> users = new ArrayList<>();
        for (Integer userId : setUsersId) {
            users.add(storage.getUserById(userId));
        }
        return users;
    }

    public User addUser(User user) {
        return storage.add(user);
    }

    public User updateUser(User user) {
        return storage.update(user);
    }

    public User getUserById(int idUser) {
        return storage.getUserById(idUser);
    }

    public void deleteAllUsers() {
        storage.deleteAll();
    }
}
