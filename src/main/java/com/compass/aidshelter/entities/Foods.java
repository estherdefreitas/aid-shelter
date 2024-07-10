package com.compass.aidshelter.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

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
    private Date expirationDate;

    public Foods(Long id, Integer quantity, String unitMeasure, Date expirationDate) {
        this.id = id;
        this.quantity = quantity;
        this.unitMeasure = unitMeasure;
        this.expirationDate = expirationDate;
    }

}
