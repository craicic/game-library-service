package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Theme's DTO
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {

    private long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThemeDto themeDto = (ThemeDto) o;
        return id == themeDto.id &&
                name.equals(themeDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
