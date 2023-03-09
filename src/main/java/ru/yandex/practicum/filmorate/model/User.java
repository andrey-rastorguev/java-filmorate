package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    private final Set<User> friends = new HashSet<>();

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

}
