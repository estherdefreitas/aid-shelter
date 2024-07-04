package entities;


import entities.enums.ClothesGender;
import entities.enums.ClothesSize;
import entities.enums.ClothesType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_clothes")
public class Clothes implements Serializable, Item {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private ClothesType name;

    private ClothesSize size;

    private ClothesGender gender;

    public Integer getQuantity() {
        return 0;
    }
}
