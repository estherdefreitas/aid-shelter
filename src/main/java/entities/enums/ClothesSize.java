package entities.enums;

import lombok.Getter;

@Getter
public enum ClothesSize {

    INFANTIL(1),
    PP(2),
    P(3),
    M(4),
    G(5),
    GG(6);

    private final int sizeCode;

    ClothesSize(int sizeCode) { this.sizeCode = sizeCode; }

    public static ClothesSize valueOf(int sizeCode) {
        for (ClothesSize cs : ClothesSize.values()) {
            if (cs.getSizeCode() == sizeCode) {
                return cs;
            }
        }
        throw new IllegalArgumentException("Invalid ClothesSize code: " + sizeCode);
    }
}
