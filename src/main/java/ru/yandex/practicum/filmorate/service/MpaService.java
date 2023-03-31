package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.records.MpaRecord;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Service
public class MpaService {

    private final MpaStorage mpaStorage;

    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<MpaRecord> getAll() {
        return mpaStorage.getAll();
    }

    public MpaRecord getMpaById(int mpaId) {
        return mpaStorage.getMpaById(mpaId);
    }
}
