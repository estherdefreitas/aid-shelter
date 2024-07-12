package com.compass.aidshelter.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
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

    private Integer quantity;
    private String unitMeasure;
    private LocalDate expirationDate;

    public Foods(Long id, String description, Integer quantity, String unitMeasure, LocalDate expirationDate) {
        super.setDescription(description);
        this.id = id;
        this.quantity = quantity;
        this.unitMeasure = unitMeasure;
        this.expirationDate = expirationDate;
    }

}
