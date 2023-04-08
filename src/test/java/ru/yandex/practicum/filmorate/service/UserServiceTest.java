package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceTest {
    @Autowired
    private UserService userService;

    @BeforeEach
    public void initial() {
        userService.deleteAllUsers();
        User user;
        for (int i = 1; i < 7; i++) {
            user = User.builder()
                    .email("e" + i + "@yandex.ru")
                    .login("login " + i)
                    .name("name " + i)
                    .birthday(LocalDate.of(1990 + 3 * i, 2 + i, 25 - 2 * i))
                    .build();
            userService.addUser(user);
        }
        userService.makeFriends(1, 3);
        userService.makeFriends(3, 5);
        user = userService.getUserById(5);
        user.acceptFriend(3);
        userService.updateUser(user);
    }


    @Test
    void shouldReturnSevenUsersWhenGetAllUsers() {
        User user = User.builder()
                .name("John")
                .login("John")
                .email("john@gmail.com")
                .birthday(LocalDate.of(2000, 12, 12))
                .name("John")
                .build();
        userService.addUser(user);
        assertEquals(7, userService.getAllUsers().size(), "Недопустимое количество пользователей");
    }

    @Test
    void shouldReturnFiveFriendsWhenWeCreateNewUserWithFriends() {
        User user = User.builder()
                .name("John")
                .login("John")
                .email("john@gmail.com")
                .birthday(LocalDate.of(2000, 12, 12))
                .name("John")
                .build();
        userService.addUser(user);
        userService.makeFriends(user.getId(), 1);
        userService.makeFriends(user.getId(), 2);
        userService.makeFriends(user.getId(), 3);
        userService.makeFriends(user.getId(), 4);
        userService.makeFriends(user.getId(), 5);
        assertEquals(5, userService.getUserById(user.getId()).getFriends().size(), "Недопустимое количество друзей");
    }

    @Test
    void shouldReturnThreeFriendWhenFindFriends() {
        userService.makeFriends(5, 6);
        userService.makeFriends(5, 1);
        assertEquals(3, userService.findFriends(5).size(), "Недопустимое количество друзей");
    }

    @Test
    void shouldReturnZeroFriendWhenRemoveFriend() {
        userService.makeFriends(5, 6);
        userService.makeFriends(5, 1);
        userService.removeFriendForUser(5, 6);
        userService.removeFriendForUser(5, 1);
        userService.removeFriendForUser(5, 3);
        assertEquals(0, userService.findFriends(5).size(), "Недопустимое количество друзей");
    }


    @Test
    void findMutualFriends() {
        assertEquals(1, userService.findMutualFriends(5, 1).size(), "Недопустимое количество общих друзей");
    }

    @Test
    void shouldReturnSecondFriendWhenGetUserById() {
        assertEquals("e2@yandex.ru", userService.getUserById(2).getEmail(), "Возвращен не верный пользователь");
    }
}