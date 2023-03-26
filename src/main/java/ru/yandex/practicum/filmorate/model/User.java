package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private final Set<Integer> friends = new HashSet<>();

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

    public void addFriend(int idFriend) {
        friends.add(idFriend);
    }

    public void removeFriend(int idFriend) {
        friends.remove(idFriend);
    }

    public List<Integer> getAllFriends() {
        return friends.stream().collect(Collectors.toList());
    }

}
