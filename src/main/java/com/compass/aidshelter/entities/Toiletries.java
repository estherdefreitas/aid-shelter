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

    private ToiletriesType type;

    public Toiletries(Long id, ToiletriesType type) {
        this.id = id;
        this.type = type;
    }

}
