package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.usingContext().getValidator();
    }

    @Test
    void validateEmailForRightValue() {
        User user = new User();
        user.setEmail("123@yandex.ru");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        long countErrors = violations.stream()
                .filter(x -> (x.getPropertyPath().toString().equals("email")))
                .count();
        assertEquals(0, countErrors, "Wrong validate email value");
    }

    @Test
    void validateEmailForNullValue() {
        User user = new User();
        user.setEmail("123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        long countErrors = violations.stream()
                .filter(x -> (x.getMessage().equals("должно иметь формат адреса электронной почты")) && (x.getPropertyPath().toString().equals("email")))
                .count();
        assertEquals(1, countErrors, "Email has wrong format");
    }

    @Test
    void validateLoginForRightValue() {
        User user = new User();
        user.setLogin("vasya123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        long countErrors = violations.stream()
                .filter(x -> (x.getPropertyPath().toString().equals("login")))
                .count();
        assertEquals(0, countErrors, "Wrong validate login value");
    }

    @Test
    void validateLoginForWrongValue() {
        User user = new User();
        user.setLogin("vasya 123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        long countErrors = violations.stream()
                .filter(x -> (x.getMessage().equals("должно соответствовать \"\\S+\"")) && (x.getPropertyPath().toString().equals("login")))
                .count();
        assertEquals(1, countErrors, "Login has wrong format");
    }

    @Test
    void validateBirthdayForRightValue() {
        User user = new User();
        user.setBirthday(LocalDate.of(2000, 10, 13));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        long countErrors = violations.stream()
                .filter(x -> (x.getPropertyPath().toString().equals("birthday")))
                .count();
        assertEquals(0, countErrors, "Wrong validate birthday value");
    }

    @Test
    void validateBirthdayForWrongValue() {
        User user = new User();
        user.setBirthday(LocalDate.of(2100, 11, 3));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        long countErrors = violations.stream()
                .filter(x -> (x.getMessage().equals("должно содержать прошедшую дату")) && (x.getPropertyPath().toString().equals("birthday")))
                .count();
        assertEquals(1, countErrors, "Birthday is in future");
    }

}