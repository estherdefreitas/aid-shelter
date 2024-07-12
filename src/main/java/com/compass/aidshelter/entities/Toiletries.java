package com.compass.aidshelter.entities;

import com.compass.aidshelter.entities.enums.ToiletriesType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tb_toileries")
public class Toiletries extends Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private ToiletriesType type;


    public Toiletries(String description,ToiletriesType type) {
        super(description);
        this.type = type;
        this.id = this.getDescription() + this.getType().name();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
        this.id = this.getDescription() + this.getType().name();
    }

    public void setType(ToiletriesType type) {
        this.type = type;
        this.id = this.getDescription() + this.getType().name();
    }

    @Override
    @Id
    public String getId() {
        return this.getDescription() + this.getType().name();
    }
}
