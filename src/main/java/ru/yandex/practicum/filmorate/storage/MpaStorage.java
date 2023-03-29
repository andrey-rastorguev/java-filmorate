package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.MpaRecord;

import java.util.List;

public interface MpaStorage {
    List<MpaRecord> getAll();
    MpaRecord getMpaById(int mpaId);
    MpaRecord getMpaByName(String codeMPA);
}
