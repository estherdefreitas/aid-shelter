package com.compass.aidshelter.entities;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_donation")
public class Donation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;


    @ManyToOne
    private DistributionCenter distributionCenter;

    private int quantity;

    @ManyToOne
    private Foods foods;
    @ManyToOne
    private Clothes clothes;
    @ManyToOne
    private Toiletries toiletries;





}
