package com.compass.aidshelter.entities.enums;


import lombok.Getter;

@Getter
public enum ToiletriesType {
    SABONETE(1),
    PASTA_DE_DENTE(2),
    ESCOVA_DE_DENTE(3),
    ABSORVENTES(4);

    private final int typeCode;

    ToiletriesType(final int typeCode) {
        this.typeCode = typeCode;
    }

    public static ToiletriesType valueOf(int typeCode) {
        for (ToiletriesType t : ToiletriesType.values()) {
            if (t.getTypeCode() == typeCode){
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid typeCode: " + typeCode);
    }


}
