package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    private void initial() {
        userService.deleteAllUsers();
        for (int i = 1; i < 11; i++) {
            userService.addUser(new User(i, "mail" + i + "@mail.ru", "user" + i, "name" + i, LocalDate.of(1985, 3, i)));
        }
    }

    @Test
    void shouldReturnTwoFriendsWhenMakeTwoFriends() {
        int userId1 = 1;
        int userId2 = 2;
        int userId3 = 3;
        userService.makeFriends(userId1, userId2);
        userService.makeFriends(userId2, userId3);
        userService.makeFriends(userId1, userId3);
        User user1 = userService.getUserById(userId1);
        User user2 = userService.getUserById(userId2);
        User user3 = userService.getUserById(userId3);
        Assertions.assertEquals(user1.getAllFriends().size(), 2, "Не верное количество друзей");
        Assertions.assertEquals(user2.getAllFriends().size(), 2, "Не верное количество друзей");
        Assertions.assertEquals(user3.getAllFriends().size(), 2, "Не верное количество друзей");
    }

    @Test
    void shouldReturnOneFriendsWhenMakeTwoFriendsAndRemoveOne() {
        int userId1 = 1;
        int userId2 = 2;
        int userId3 = 3;
        userService.makeFriends(userId1, userId2);
        userService.makeFriends(userId2, userId3);
        userService.makeFriends(userId1, userId3);
        userService.removeFriendForUser(userId1, userId3);
        User user1 = userService.getUserById(userId1);
        User user2 = userService.getUserById(userId2);
        User user3 = userService.getUserById(userId3);
        Assertions.assertEquals(user1.getAllFriends().size(), 1, "Не верное количество друзей");
        Assertions.assertEquals(user2.getAllFriends().size(), 2, "Не верное количество друзей");
        Assertions.assertEquals(user3.getAllFriends().size(), 2, "Не верное количество друзей");
    }

    @Test
    void shouldReturnMutualFriends() {
        int userId1 = 1;
        int userId2 = 2;
        int userId3 = 3;
        int userId4 = 4;
        int userId5 = 5;
        userService.makeFriends(userId1, userId2);
        userService.makeFriends(userId2, userId3);
        userService.makeFriends(userId1, userId3);
        userService.makeFriends(userId1, userId5);
        userService.makeFriends(userId4, userId5);
        User user5 = userService.getUserById(5);
        Assertions.assertEquals(1, userService.findMutualFriends(userId1, userId4).size(), "Не верное количество общих друзей");
        Assertions.assertTrue(userService.findMutualFriends(userId1, userId4).contains(user5), "Не верный общий друг");
    }

}