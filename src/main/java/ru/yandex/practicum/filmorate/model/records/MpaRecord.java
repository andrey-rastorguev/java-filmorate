package ru.yandex.practicum.filmorate.model.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.filmorate.other.Constants;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class MpaRecord {
    private int id;
    private String name;

    public MpaRecord(int id) {
        this.id = id;
        this.name = Constants.MPA_VALUES.get(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MpaRecord record = (MpaRecord) o;
        return id == record.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
