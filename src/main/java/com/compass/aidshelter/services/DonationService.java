package com.compass.aidshelter.services;

import com.compass.aidshelter.repositories.ClothesRepository;
import com.compass.aidshelter.repositories.DistributionCenterRepository;
import com.compass.aidshelter.repositories.FoodsRepository;
import com.compass.aidshelter.repositories.ToiletriesRepository;

public class DonationService {
    private final ClothesRepository clothesRepository;
    private final FoodsRepository foodsRepository;
    private final ToiletriesRepository toiletriesRepository;
    private final DistributionCenterRepository distributionCenterRepository;

    public DonationService(
            ClothesRepository clothesRepository,
            FoodsRepository foodsRepository,
            ToiletriesRepository toiletriesRepository,
            DistributionCenterRepository distributionCenterRepository
    ) {
        this.clothesRepository = clothesRepository;
        this.foodsRepository = foodsRepository;
        this.toiletriesRepository = toiletriesRepository;
        this.distributionCenterRepository = distributionCenterRepository;
    }

    public void saveDonations(String donationFilePath) {

    }
}
