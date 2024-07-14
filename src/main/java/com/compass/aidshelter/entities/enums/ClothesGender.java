package com.compass.aidshelter.entities.enums;

import lombok.Getter;

@Getter
public enum ClothesGender {
    M,
    F;

    public static boolean isValidClothesGender(String value) {
        try {
            ClothesGender.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
