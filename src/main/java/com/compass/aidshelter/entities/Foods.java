package com.compass.aidshelter.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tb_foods")
public class Foods extends Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private Integer quantity;
    private String unitMeasure;

    public Foods(String description, Integer quantity, String unitMeasure) {
        super(description);
        this.id = description + quantity.toString() + unitMeasure;
        this.quantity = quantity;
        this.unitMeasure = unitMeasure;
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
        this.id = description + quantity.toString() + unitMeasure;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.id = this.getDescription() + quantity.toString() + unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
        this.id = this.getDescription() + quantity.toString() + unitMeasure;
    }

    @Override
    public String getId() {
        return getDescription() + this.getQuantity().toString() + this.getUnitMeasure();
    }
}
