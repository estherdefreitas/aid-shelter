package com.compass.aidshelter.entities;

import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public abstract class Item {
    private String description;

    @OneToOne(mappedBy = "id.item")
    private Set<DonationItem> donationItem = new HashSet<>();


}
