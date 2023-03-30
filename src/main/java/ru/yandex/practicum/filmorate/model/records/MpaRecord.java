package ru.yandex.practicum.filmorate.model.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.filmorate.other.Constants;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Getter
@Setter
public class MpaRecord {
    @Positive
    private int id;
    @Pattern(regexp = "^[a-z]([a-zA-Z0-9]*)?$")
    private String name;

    public MpaRecord(@Positive int id) {
        this.id = id;
    }

    public MpaRecord(@Positive int id, @Pattern(regexp = "^[a-z]([a-zA-Z0-9]*)?$") String name) {
        this.id = id;
        if (name == null) {
            this.name = Constants.MPA_VALUES.get(id);
        } else {
            this.name = name;
        }
    }

    public String getName() {
        if (name == null) {
            return Constants.MPA_VALUES.get(id);
        } else {
            return name;
        }
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
