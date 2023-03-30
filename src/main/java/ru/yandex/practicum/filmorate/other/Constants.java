package ru.yandex.practicum.filmorate.other;

import java.util.Map;

public class Constants {
    public static final Map<Integer, String> MPA_VALUES = Map.of(
            1, "G",
            2, "PG",
            3, "PG-13",
            4, "R",
            5, "NC-17");
    public static final Map<String, Integer> MPA_KEYS = Map.of(
            "G", 1,
            "PG", 2,
            "PG-13", 3,
            "R", 4,
            "NC-17", 5);

}
