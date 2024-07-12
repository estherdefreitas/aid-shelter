package com.compass.aidshelter.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
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
    private String responsible;
    private String phone;

    public DistributionCenter(Long id, String name, String address, String responsible, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.responsible = responsible;
        this.phone = phone;
    }

    @OneToMany(mappedBy = "donationItemPk.distributionCenter", cascade = CascadeType.ALL)
    private List<DonationItem> donationItems = new ArrayList<>();

}
