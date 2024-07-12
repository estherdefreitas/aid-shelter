package com.compass.aidshelter.repositories;

import com.compass.aidshelter.entities.Donation;

public class DonationRepository extends Repository<Donation> {
    public DonationRepository() {
        super(Donation.class);
    }
}
