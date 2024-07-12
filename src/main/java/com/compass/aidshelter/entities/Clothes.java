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

    private String name;

    private ClothesSize size;

    private ClothesGender gender;

    public Clothes(Long id, String name, ClothesSize size, ClothesGender gender) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.gender = gender;
    }


}
