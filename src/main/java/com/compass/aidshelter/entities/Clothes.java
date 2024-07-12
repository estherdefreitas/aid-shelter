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
@Entity
@Table(name = "tb_clothes")
public class Clothes extends Item implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private ClothesSize size;

    @Enumerated(EnumType.STRING)
    private ClothesGender gender;

    public Clothes(String description, ClothesSize size, ClothesGender gender) {
        super(description);
        this.id = description + size.name() + gender.name();
        this.size = size;
        this.gender = gender;
    }

    public void setSize(ClothesSize size) {
        this.size = size;
        this.id = this.getDescription() + this.getSize().name() + this.getGender().name();
    }
    public void setGender(ClothesGender gender) {
        this.gender = gender;
        this.id = this.getDescription() + this.getSize() + this.getGender().name();
    }

    @Override
    public String getId() {
        return this.getDescription() + this.getSize().name() + this.getGender().name();
    }
}
