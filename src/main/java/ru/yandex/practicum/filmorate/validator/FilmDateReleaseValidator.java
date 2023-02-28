package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FilmDateReleaseValidator implements ConstraintValidator<FilmDateReleaseDateTrue, LocalDate> {
    private final LocalDate LAST_DAY_OF_THE_FILMLESS_ERA = LocalDate.of(1895, 12, 27);

    @Override
    public void initialize(FilmDateReleaseDateTrue constraintAnnotation) {}

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value != null ? value.isAfter(LAST_DAY_OF_THE_FILMLESS_ERA) : false;
    }
}
