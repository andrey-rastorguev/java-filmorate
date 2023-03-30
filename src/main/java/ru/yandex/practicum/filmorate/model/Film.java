package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.records.GenreRecord;
import ru.yandex.practicum.filmorate.model.records.MpaRecord;
import ru.yandex.practicum.filmorate.validator.FilmDateReleaseDateTrue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class Film {

    @Positive
    private int id = 1;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;

    private MpaRecord MPA;

    @FilmDateReleaseDateTrue
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private Set<GenreRecord> genres;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Set<Integer> likes;

    public Film() {
        this.genres = new HashSet<>();
        this.likes = new HashSet<>();
    }

    public Film(@Positive int id, @NotBlank String name, @Size(max = 200) String description, @Pattern(regexp = "^[a-z]([a-zA-Z0-9]*)?$") MpaRecord MPA, LocalDate releaseDate, @Positive int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.MPA = MPA;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = new HashSet<>();
        this.likes = new HashSet<>();
    }

    public Film(@Positive int id, @NotBlank String name, @Size(max = 200) String description,  @Pattern(regexp = "^[a-z]([a-zA-Z0-9]*)?$") MpaRecord MPA, LocalDate releaseDate, @Positive int duration, List<GenreRecord> genres, Set<Integer> likes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.MPA = MPA;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres.stream().collect(Collectors.toSet());
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id;
    }

    public List<GenreRecord> getGenres() {
        return genres.stream().collect(Collectors.toList());
    }

    public Set<GenreRecord> createSetGenres() {
        return genres;
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
        return likes.stream().collect(Collectors.toList());
    }
}
