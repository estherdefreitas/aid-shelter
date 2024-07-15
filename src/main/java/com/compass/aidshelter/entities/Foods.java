package com.compass.aidshelter.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "tb_foods")
public class Foods extends Item  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Integer quantityFood;
    private String unitMeasure;
    private LocalDate expirationDate;

    public Foods(Long id, String description, Integer quantityFood, String unitMeasure, LocalDate expirationDate) {
        super.setDescription(description);
        this.id = id;
        this.quantityFood = quantityFood;
        this.unitMeasure = unitMeasure;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return super.getId();
    }

    @Override
    public String toString() {
        return "Id da comida: " + super.getId() + "\nDescrição: " + getDescription() + "\nQuantidade: " + quantityFood + unitMeasure + "\nData de Expiração: " + expirationDate;
    }

}
