package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.records.GenreRecord;
import ru.yandex.practicum.filmorate.model.records.MpaRecord;
import ru.yandex.practicum.filmorate.validator.FilmDateReleaseDateTrue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    @Positive
    private int id = 1;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;

    private MpaRecord mpa;

    @FilmDateReleaseDateTrue
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private Set<GenreRecord> genres;

    private Set<Integer> likes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id;
    }

    public void addGenres(GenreRecord genre) {
        if (genres == null) {
            genres = new HashSet<>();
        }
        genres.add(genre);
    }

    public List<GenreRecord> getGenres() {
        if (genres == null) {
            return new ArrayList<>();
        } else {
            return genres.stream().collect(Collectors.toList());
        }
    }

    public Set<GenreRecord> createSetGenres() {
        if (genres == null) {
            return new HashSet<>();
        } else {
            return genres;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addLike(int idUser) {
        likes.add(idUser);
    }

    public void removeLike(int idUser) {
        likes.remove(idUser);
    }

    public int countLikes() {
        return likes.size();
    }

    public List<Integer> getLikes() {
        if (likes == null) {
            return new ArrayList<>();
        } else {
            return likes.stream().collect(Collectors.toList());
        }
    }
}
