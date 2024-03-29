package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmTest {
    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.usingContext().getValidator();
    }

    @Test
    void validateNameForNull() {
        Film film = new Film();
        film.setName(null);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        long countErrors = violations.stream()
                .filter(x -> (x.getMessageTemplate().equals("{javax.validation.constraints.NotBlank.message}")) && (x.getPropertyPath().toString().equals("name")))
                .count();
        assertEquals(1, countErrors, "Name is empty");
    }

    @Test
    void validateNameForOnlySpaceSymbols() {
        Film film = new Film();
        film.setName("  ");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        long countErrors = violations.stream()
                .filter(x -> (x.getMessageTemplate().equals("{javax.validation.constraints.NotBlank.message}")) && (x.getPropertyPath().toString().equals("name")))
                .count();
        assertEquals(1, countErrors, "Name is empty");
    }

    @Test
    void validateNameForCorrectValue() {
        Film film = new Film();
        film.setName("qwerqw erqw erqw");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        long countErrors = violations.stream()
                .filter(x -> (x.getPropertyPath().toString().equals("name")))
                .count();
        assertEquals(0, countErrors, "Wrong validate name field");
    }

    @Test
    void validateDurationForCorrectValue() {
        Film film = new Film();
        film.setDuration(6);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        long countErrors = violations.stream()
                .filter(x -> (x.getPropertyPath().toString().equals("duration")))
                .count();
        assertEquals(0, countErrors, "Wrong validate duration field");
    }

    @Test
    void validateDurationForZeroValue() {
        Film film = new Film();
        film.setDuration(0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        long countErrors = violations.stream()
                .filter(x -> (x.getMessageTemplate().equals("{javax.validation.constraints.Positive.message}")) && (x.getPropertyPath().toString().equals("duration")))
                .count();
        assertEquals(1, countErrors, "Size duration is not more 0");
    }

    @Test
    void validateDurationForNegativeValue() {
        Film film = new Film();
        film.setDuration(-6);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        long countErrors = violations.stream()
                .filter(x -> (x.getMessageTemplate().equals("{javax.validation.constraints.Positive.message}")) && (x.getPropertyPath().toString().equals("duration")))
                .count();
        assertEquals(1, countErrors, "Size duration is negative");
    }

    @Test
    void validateDescriptionForCorrectSize() {
        Film film = new Film();
        film.setDescription("qwerqw erqw erqw");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        long countErrors = violations.stream()
                .filter(x -> (x.getPropertyPath().toString().equals("description")))
                .count();
        assertEquals(0, countErrors, "Wrong validate description field");
    }

    @Test
    void validateDescriptionForTooBigSize() {
        Film film = new Film();
        film.setDescription("a".repeat(300));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        long countErrors = violations.stream()
                .filter(x -> (x.getMessageTemplate().equals("{javax.validation.constraints.Size.message}")) && (x.getPropertyPath().toString().equals("description")))
                .count();
        assertEquals(1, countErrors, "Size description is more 200");
    }

    @Test
    void validateReleaseDateForCorrectValue() {
        Film film = new Film();
        film.setReleaseDate(LocalDate.of(1995, 1, 14));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        long countErrors = violations.stream()
                .filter(x -> (x.getPropertyPath().toString().equals("releaseDate")))
                .count();
        assertEquals(0, countErrors, "Wrong validate releaseDate field");

    }

    @Test
    void validateReleaseDateForTooEarlierDate() {
        Film film = new Film();
        film.setReleaseDate(LocalDate.of(1810, 11, 1));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        long countErrors = violations.stream()
                .filter(x -> (x.getMessageTemplate().equals("Release date too early")) && (x.getPropertyPath().toString().equals("releaseDate")))
                .count();
        assertEquals(1, countErrors, "Release date too early");
    }


}