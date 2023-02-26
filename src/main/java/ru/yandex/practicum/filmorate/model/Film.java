package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.other.ValidationException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Film {
    private static int genNextId = 1;
    @Positive
    private int id = genNextId;
    @NotBlank
    private String name;
    @Pattern(regexp = ".{0,200}")
    private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;

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

    public void valid() throws ValidationException {
        LocalDate earliestReleaseDate = LocalDate.of(1895, 12, 28);
        String message;
        if (getReleaseDate().isBefore(earliestReleaseDate)) {
            message = "Film: Слишком ранняя дата релиза";
            log.warn(message);
            throw new ValidationException(message);
        }
    }

    public static void incrementNextId() {
        genNextId++;
    }

}
