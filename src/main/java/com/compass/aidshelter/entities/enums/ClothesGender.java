package com.compass.aidshelter.entities.enums;

import lombok.Getter;

@Getter
public enum ClothesGender {
    M(1),
    F(2);

    private final int genderCode;

    ClothesGender(int genderCode) {
        this.genderCode = genderCode;
    }

    public static ClothesGender valueOf(int genderCode) {
        for (ClothesGender gender : ClothesGender.values()) {
            if (gender.getGenderCode() == genderCode) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender code: " + genderCode);
    }
}
