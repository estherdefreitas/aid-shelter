package com.compass.aidshelter.dto;

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
}
