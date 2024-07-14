package com.compass.aidshelter.entities.enums;


import lombok.Getter;

@Getter
public enum ToiletriesType {
    SABONETE,
    PASTA_DE_DENTE,
    ESCOVA_DE_DENTE,
    ABSORVENTE;

    public static boolean isValidToiletriesType(String value) {
        try {
            ToiletriesType.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
