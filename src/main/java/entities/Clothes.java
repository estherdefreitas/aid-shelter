package entities;


import entities.enums.ClothesGender;
import entities.enums.ClothesSize;
import entities.enums.ClothesType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
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

    public Clothes(Long id, ClothesType name, ClothesSize size, ClothesGender gender) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.gender = gender;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void setDescription(String description) {

    }
}
