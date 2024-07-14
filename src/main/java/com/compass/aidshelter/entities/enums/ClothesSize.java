package com.compass.aidshelter.entities.enums;

import lombok.Getter;

@Getter
public enum ClothesSize {

    INFANTIL,
    PP,
    P,
    M,
    G,
    GG;

    public static boolean isValidClothesSize(String value) {
        try {
            ClothesSize.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
