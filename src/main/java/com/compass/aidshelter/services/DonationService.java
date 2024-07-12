package com.compass.aidshelter.services;


import com.compass.aidshelter.dtos.DonationDto;
import com.compass.aidshelter.entities.*;
import com.compass.aidshelter.entities.enums.ClothesGender;
import com.compass.aidshelter.entities.enums.ClothesSize;
import com.compass.aidshelter.entities.enums.ToiletriesType;
import com.compass.aidshelter.input.DonationReader;
import com.compass.aidshelter.repositories.ClothesRepository;
import com.compass.aidshelter.repositories.DistributionCenterRepository;
import com.compass.aidshelter.repositories.FoodsRepository;
import com.compass.aidshelter.repositories.ToiletriesRepository;

import java.util.List;

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
        List<DonationDto> donationDtos = DonationReader.readDonationsFromCsv(donationFilePath);
        int threshold = 1000;

        for (DonationDto donationDto : donationDtos) {
            DistributionCenter distributionCenter = distributionCenterRepository.findById(Long.parseLong(donationDto.destination()));
            if(distributionCenter == null){
                throw new IllegalArgumentException("DistributionCenter not found: " + donationDto.destination());
            }
            switch (donationDto.itemType()) {
                    case "roupa":
                        Clothes clothes = clothesRepository.findById(donationDto.description() + donationDto.size() + donationDto.gender());
                        if(clothes == null) {
                            clothes = new Clothes(donationDto.description(), ClothesSize.valueOf(donationDto.size()), ClothesGender.valueOf(donationDto.gender()));
                            clothesRepository.save(clothes);
                            clothes = clothesRepository.findById(donationDto.description() + donationDto.size() + donationDto.gender());
                        }
                        DonationItem donationItem = new DonationItem(null, distributionCenter, clothes, donationDto.quantityItem());
                        distributionCenter.getDonationItems().add(donationItem);
                        distributionCenterRepository.save(distributionCenter);
                        break;
                    case "higiene":
                        Toiletries toiletries = new Toiletries(
                                donationDto.description(),
                                ToiletriesType.valueOf(donationDto.description().toUpperCase().replace(" ","_")));
                        break;
                    case "comida":
//                        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
                        Foods foods = new Foods(
                                donationDto.description(),
                                donationDto.quantityFood(),
                                donationDto.unitMeasure()
                        );
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown item type: " + donationDto.itemType());
                }

        }

    }
}
