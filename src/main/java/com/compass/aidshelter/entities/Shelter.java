package com.compass.aidshelter.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;



@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "tb_shelter")
@AllArgsConstructor
public class Shelter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String responsible;
    private String phone;
    private String email;
    private String storageCapacity;
    private String occupationPercentage;

}
