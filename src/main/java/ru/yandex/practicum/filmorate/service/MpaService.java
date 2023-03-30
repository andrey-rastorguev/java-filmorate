package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.records.MpaRecord;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Service
public class MpaService {
    @Autowired
    private MpaStorage mpaStorage;

    public List<MpaRecord> getAll() {
        return mpaStorage.getAll();
    }

    public MpaRecord getMpaById(int mpaId) {
        return mpaStorage.getMpaById(mpaId);
    }
}
