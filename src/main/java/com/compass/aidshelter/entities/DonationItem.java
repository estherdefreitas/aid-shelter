package com.compass.aidshelter.entities;


import com.compass.aidshelter.entities.pk.DonationItemPk;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_donation_item")
@Entity
@IdClass(DonationItemPk.class)
public class DonationItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DonationItemPk donationItemPk;

    @ManyToOne
    @MapsId("distributionCenterId")
    private DistributionCenter distributionCenter;

    @ManyToOne
    @MapsId("itemId")
    private Item item;

    private Integer quantity;
}
