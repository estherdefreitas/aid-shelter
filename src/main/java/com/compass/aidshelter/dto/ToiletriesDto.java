package com.compass.aidshelter.dto;

import com.compass.aidshelter.entities.Toiletries;
import com.compass.aidshelter.entities.enums.ToiletriesType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ToiletriesDto {
    private String description;
    private String typeToiletries;

    public Toiletries toEntity(Long id) {
        return new Toiletries(id, description, ToiletriesType.valueOf(typeToiletries));
    }
}
