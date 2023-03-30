package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void shouldReturnSixUsersWhenGetAllUsers() {
        assertEquals(6, userService.getAllUsers().size(), "Недопустимое количество пользователей");
    }

    @Test
    void shouldReturnOneFriendWhenfindFriends() {
        assertEquals(1, userService.findFriends(5).size(), "Недопустимое количество друзей");
    }

    @Test
    void findMutualFriends() {
        assertEquals(1, userService.findMutualFriends(5, 1).size(), "Недопустимое количество общих друзей");
    }

    @Test
    void shouldReturnSecondFriendWhenGetUserById() {
        assertEquals("kolya@yandex.ru", userService.getUserById(2).getEmail(), "Возвращен не верный пользователь");
    }
}