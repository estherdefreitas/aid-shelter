package com.compass.aidshelter.entities.enums;


import lombok.Getter;

import java.util.Objects;

@Getter
public enum ClothesType {

    CAMISA("Camisa"),
    AGASALHO("Agasalho");

    private final String name;

    ClothesType(String name) { this.name = name; }

}
