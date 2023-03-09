package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.validator.FilmDateReleaseDateTrue;

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
public class Film {

    @Positive
    private int id = 1;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @FilmDateReleaseDateTrue
    private LocalDate releaseDate;
    @Positive
    private int duration;

    private final Set<User> likes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
