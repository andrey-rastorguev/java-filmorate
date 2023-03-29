package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class User {
    @Positive
    private int id = 1;
    @Email
    private String email;
    @Pattern(regexp = "\\S+")
    private String login;
    private String name;
    @Past
    private LocalDate birthday;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final Map<Integer,Boolean> friends;

    public User() {
        this.friends = new HashMap<>();
    }

    public User(@Positive int id, @Email String email, @Pattern(regexp = "\\S+") String login, String name, @Past LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = new HashMap<>();
    }

    public User(@Positive int id, @Email String email, @Pattern(regexp = "\\S+") String login, String name, @Past LocalDate birthday, Map<Integer, Boolean> friends) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = friends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getName() {
        if (name == null || name.equals("")) {
            return login;
        } else {
            return name;
        }
    }


    public void addFriend(int friendId) {
        friends.put(friendId,false);
    }

    public void acceptFriend(int friendId) {
        friends.put(friendId,true);
    }

    public void removeFriend(int idFriend) {
        friends.remove(idFriend);
    }

    public List<Integer> getFriends() {
        return friends.keySet().stream().filter(id -> friends.get(id)).collect(Collectors.toList());
    }

    public Map<Integer,Boolean> allFriendships() {
        return friends;
    }

}
