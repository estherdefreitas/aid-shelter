package com.compass.aidshelter.entities.pk;

import com.compass.aidshelter.entities.DistributionCenter;
import com.compass.aidshelter.entities.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class DonationItemPk implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "distribution_center_id")
    private DistributionCenter distributionCenter;

    @Column(name = "item_id")
    private Item item;

}
