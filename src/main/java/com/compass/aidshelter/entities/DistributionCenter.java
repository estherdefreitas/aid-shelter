package com.compass.aidshelter.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_distribution_center")
public class DistributionCenter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String address;

    @OneToMany(mappedBy = "distributionCenter")
    private List<Donation> donations;



    public DistributionCenter(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

}
