package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FilmDateReleaseValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FilmDateReleaseDateTrue {
    String message() default "Release date too early";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
