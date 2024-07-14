package com.compass.aidshelter.dto;

import com.compass.aidshelter.entities.Clothes;
import com.compass.aidshelter.entities.enums.ClothesGender;
import com.compass.aidshelter.entities.enums.ClothesSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClothesDto {
    private String description;
    private String size;
    private String gender;


    public Clothes toEntity(Long id) {
        return new Clothes(id, description, ClothesSize.valueOf(size), ClothesGender.valueOf(gender));
    }
}
