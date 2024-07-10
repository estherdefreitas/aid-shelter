package com.compass.aidshelter.entities.enums;


import lombok.Getter;

@Getter
public enum ToileriesType {
    SOAP(1),
    TOOTHPASTE(2),
    TOOTHBRUSH(3),
    TAMPONS(4);

    private final int typeCode;

    ToileriesType(final int typeCode) {
        this.typeCode = typeCode;
    }

    public static ToileriesType valueOf(int typeCode) {
        for (ToileriesType t : ToileriesType.values()) {
            if (t.getTypeCode() == typeCode){
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid typeCode: " + typeCode);
    }


}
