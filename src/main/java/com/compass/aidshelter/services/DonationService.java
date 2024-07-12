package com.compass.aidshelter.services;

import com.compass.aidshelter.dto.DonationDto;
import com.compass.aidshelter.entities.*;
import com.compass.aidshelter.entities.enums.ClothesGender;
import com.compass.aidshelter.entities.enums.ClothesSize;
import com.compass.aidshelter.entities.enums.ToiletriesType;
import com.compass.aidshelter.input.DonationReader;
import com.compass.aidshelter.repositories.*;

import java.util.List;
import java.util.Optional;

public class DonationService {
    private final ClothesRepository clothesRepository;
    private final FoodsRepository foodsRepository;
    private final ToiletriesRepository toiletriesRepository;
    private final DistributionCenterRepository distributionCenterRepository;
    private final DonationRepository donationRepository;

    public DonationService(
            ClothesRepository clothesRepository,
            FoodsRepository foodsRepository,
            ToiletriesRepository toiletriesRepository,
            DistributionCenterRepository distributionCenterRepository,
            DonationRepository donationRepository
    ) {
        this.clothesRepository = clothesRepository;
        this.foodsRepository = foodsRepository;
        this.toiletriesRepository = toiletriesRepository;
        this.distributionCenterRepository = distributionCenterRepository;
        this.donationRepository = donationRepository;
    }

    public void saveDonations(String donationFilePath) {
        List<DonationDto> donationDtos = DonationReader.readDonationsFromCsv(donationFilePath);
        Donation donation;
        int threshold = 1000;

        for (DonationDto donationDto : donationDtos) {
            DistributionCenter distributionCenter = distributionCenterRepository.findById(Long.parseLong(donationDto.destination()));
            if(distributionCenter == null){
                throw new IllegalArgumentException("DistributionCenter not found: " + donationDto.destination());
            }
            switch (donationDto.itemType()) {
                case "roupa":
                    Optional<Clothes> optionalClothes = clothesRepository.findByValues(donationDto.description(), ClothesSize.valueOf(donationDto.size()), ClothesGender.valueOf(donationDto.gender()));
                    if(optionalClothes.isEmpty()) {
                        Clothes clothes = new Clothes(null, donationDto.description(), ClothesSize.valueOf(donationDto.size()), ClothesGender.valueOf(donationDto.gender()));
                        clothesRepository.save(clothes);
                        optionalClothes = clothesRepository.findByValues(donationDto.description(), ClothesSize.valueOf(donationDto.size()), ClothesGender.valueOf(donationDto.gender()));
                    }
                    donation = new Donation((Long) null, distributionCenter, donationDto.quantityItem(), null,optionalClothes.get(),null);
                    donationRepository.save(donation);
                    break;
                case "comida":
                    Optional<Foods> optionalFoods = foodsRepository.findByValues(donationDto.description(),donationDto.quantityFood(),donationDto.unitMeasure(),donationDto.expirationDate());
                    if(optionalFoods.isEmpty()) {
                        Foods foods = new Foods(null,donationDto.description(),donationDto.quantityFood(),donationDto.unitMeasure(),donationDto.expirationDate());
                        foodsRepository.save(foods);
                        optionalFoods = foodsRepository.findByValues(donationDto.description(),donationDto.quantityFood(),donationDto.unitMeasure(),donationDto.expirationDate());
                    }
                    donation = new Donation(null, distributionCenter, donationDto.quantityItem(), optionalFoods.get(), null, null);

                    donationRepository.save(donation);
                    break;
                case "higiene":
                    Optional<Toiletries> optionalToiletries = toiletriesRepository.findByValues(donationDto.description(), ToiletriesType.valueOf(donationDto.typeToiletries().replace(" ","_").toUpperCase()));
                    if(optionalToiletries.isEmpty()) {
                        Toiletries toiletries = new Toiletries(null,donationDto.description(),ToiletriesType.valueOf(donationDto.typeToiletries().replace(" ","_").toUpperCase()));
                        toiletriesRepository.save(toiletries);
                        optionalToiletries = toiletriesRepository.findByValues(donationDto.description(), ToiletriesType.valueOf(donationDto.typeToiletries().replace(" ","_").toUpperCase()));
                    }
                    donation = new Donation(null, distributionCenter, donationDto.quantityItem(), null, null,optionalToiletries.get());
                    donationRepository.save(donation);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown item type: " + donationDto.itemType());
            }

        }


    }
}
