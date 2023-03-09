package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        return userService.getStorage().add(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void makeFriends(@PathVariable("id") int userId, @PathVariable("friendId") int friendId) {
        userService.makeFriends(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriends(@PathVariable("id") int userId, @PathVariable("friendId") int friendId) {
        return userService.removeFriendForUser(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> findFriends(@PathVariable("id") int userId) {
        return userService.findFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findMutualFriends(@PathVariable("id") int userId, @PathVariable("otherId") int userOtherId) {
        return userService.findMutualFriends(userId, userOtherId);
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        userService.getStorage().update(user);
        return user;
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") int idUser) {
        return userService.getStorage().getUserById(idUser);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
