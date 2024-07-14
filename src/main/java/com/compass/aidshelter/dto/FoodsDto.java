package com.compass.aidshelter.dto;

import com.compass.aidshelter.entities.Foods;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FoodsDto {
    private String description;
    private Integer quantityFood;
    private String unitMeasure;
    private LocalDate expirationDate;

    public Foods toEntity(Long id) {
        return new Foods(id, description, quantityFood, unitMeasure, expirationDate);
    }
}
