package com.compass.aidshelter.entities;

import com.compass.aidshelter.entities.enums.ToiletriesType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "tb_toiletries")
public class Toiletries extends Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    private ToiletriesType type;

    public Toiletries(Long id, String description, ToiletriesType type) {
        super.setDescription(description);
        this.id = id;
        this.type = type;
    }
    public Long getId() {
        return super.getId();
    }

    @Override
    public String toString() {
        return "Id do produto de higiene: " + super.getId() + "\nDescrição: " + getDescription() + "\nTipo: " + type;
    }

}
