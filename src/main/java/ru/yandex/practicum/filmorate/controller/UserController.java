package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.other.UpdateException;
import ru.yandex.practicum.filmorate.other.ValidationException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private List<User> users = new ArrayList<>();

    @PostMapping
    public User create(@RequestBody @Valid User user) throws ValidationException {
        users.add(user);
        User.incrementNextId();
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) throws ValidationException, UpdateException {
        if (users.contains(user)) {
            users.remove(user);
            users.add(user);
        } else {
            throw new UpdateException("User: Не найден объект для обновления");
        }
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

}
