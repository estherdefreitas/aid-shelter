package com.compass.aidshelter.entities;

import com.compass.aidshelter.entities.enums.ToileriesType;
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
@Table(name = "tb_toileries")
public class Toileries extends Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private ToileriesType type;

    public Toileries(Long id, ToileriesType type) {
        this.id = id;
        this.type = type;
    }

}
