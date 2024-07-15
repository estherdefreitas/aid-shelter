package com.compass.aidshelter.enums;

import lombok.Getter;

@Getter
public enum ItemTypes {
    COMIDA,
    HIGIENE,
    ROUPA;


    public static ItemTypes domainToItemTypes(String type) {
        return switch (type) {
            case "Foods" -> COMIDA;
            case "Toiletries" -> HIGIENE;
            case "Clothes" -> ROUPA;
            default -> throw new IllegalArgumentException("Invalid item type: " + type);
        };
    }

    public static String itemTypesToDomain(ItemTypes type) {
        return switch (type) {
            case COMIDA -> "Foods";
            case HIGIENE -> "Toiletries";
            case ROUPA -> "Clothes";
        };
    }
}
