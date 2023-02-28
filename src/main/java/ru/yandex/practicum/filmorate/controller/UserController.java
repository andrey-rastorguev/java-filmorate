package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.other.UpdateException;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        userRepository.add(user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) throws UpdateException {
        userRepository.update(user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

}
