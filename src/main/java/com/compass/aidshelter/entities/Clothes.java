package com.compass.aidshelter.entities;


import com.compass.aidshelter.entities.enums.ClothesGender;
import com.compass.aidshelter.entities.enums.ClothesSize;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "tb_clothes")
public class Clothes extends Item implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    private ClothesSize size;

    @Enumerated(EnumType.STRING)
    private ClothesGender gender;

    public Clothes(Long id, String description, ClothesSize size, ClothesGender gender) {
        super.setDescription(description);
        this.id = id;
        this.size = size;
        this.gender = gender;
    }


}
