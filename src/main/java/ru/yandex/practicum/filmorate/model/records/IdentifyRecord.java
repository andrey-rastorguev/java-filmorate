package ru.yandex.practicum.filmorate.model.records;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentifyRecord {
    private int id;

    public IdentifyRecord(int id) {
        this.id = id;
    }
}
