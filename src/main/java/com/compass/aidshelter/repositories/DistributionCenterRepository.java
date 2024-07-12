package com.compass.aidshelter.repositories;


import com.compass.aidshelter.entities.DistributionCenter;

public class DistributionCenterRepository extends Repository<DistributionCenter,Long> {
    public DistributionCenterRepository() {
        super(DistributionCenter.class);
    }
}
