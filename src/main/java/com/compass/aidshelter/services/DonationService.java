package com.compass.aidshelter.services;

import com.compass.aidshelter.dto.ClothesDto;
import com.compass.aidshelter.dto.DonationDto;
import com.compass.aidshelter.dto.FoodsDto;
import com.compass.aidshelter.dto.ToiletriesDto;
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
    private final int threshold = 1000;

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

    public void loadDonations(String donationFilePath) {
        List<DonationDto> donationDtos = DonationReader.readDonationsFromCsv(donationFilePath);
        Donation donation;

        for (DonationDto donationDto : donationDtos) {
            DistributionCenter distributionCenter = distributionCenterRepository.findById(Long.parseLong(donationDto.destination()));
            if(distributionCenter == null){
                throw new IllegalArgumentException("DistributionCenter not found: " + donationDto.destination());
            }
            switch (donationDto.itemType()) {
                case "roupa":
                    saveClothesDonation(donationDto.toClothesDto(), distributionCenter, donationDto.quantityItem());
                    break;
                case "comida":
                    saveFoodsDonation(donationDto.toFoodsDto(), distributionCenter, donationDto.quantityItem());
                    break;
                case "higiene":
                    saveToiletriesDonation(donationDto.toToiletriesDto(), distributionCenter, donationDto.quantityItem());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown item type: " + donationDto.itemType());
            }

        }
    }
    public void saveClothesDonation(ClothesDto donationDto, DistributionCenter distributionCenter, Integer quantity){
        if(canDistributionCenterReceiveItemOfType("roupa", quantity)) {
            Optional<Clothes> optionalClothes = clothesRepository.findByValues(donationDto.getDescription(), ClothesSize.valueOf(donationDto.getSize()), ClothesGender.valueOf(donationDto.getGender()));
            if(optionalClothes.isEmpty()) {
                Clothes clothes = new Clothes(null, donationDto.getDescription(), ClothesSize.valueOf(donationDto.getSize()), ClothesGender.valueOf(donationDto.getGender()));
                clothesRepository.save(clothes);
                optionalClothes = clothesRepository.findByValues(donationDto.getDescription(), ClothesSize.valueOf(donationDto.getSize()), ClothesGender.valueOf(donationDto.getGender()));
            }
            Donation donation = new Donation((Long) null, distributionCenter, quantity, null,optionalClothes.get(),null);
            donationRepository.save(donation);
        }
    }

    public boolean canDistributionCenterReceiveItemOfType(String type, Integer quantity) {
        return (donationRepository.countAllDonationsByType(type) + quantity) < threshold;
    }

    public void saveFoodsDonation(FoodsDto donationDto, DistributionCenter distributionCenter, Integer quantity){
        if(canDistributionCenterReceiveItemOfType("comida", quantity)) {
            Optional<Foods> optionalFoods = foodsRepository.findByValues(donationDto.getDescription(),donationDto.getQuantityFood(),donationDto.getUnitMeasure(),donationDto.getExpirationDate());
            if(optionalFoods.isEmpty()) {
                Foods foods = new Foods(null,donationDto.getDescription(),donationDto.getQuantityFood(),donationDto.getUnitMeasure(),donationDto.getExpirationDate());
                foodsRepository.save(foods);
                optionalFoods = foodsRepository.findByValues(donationDto.getDescription(),donationDto.getQuantityFood(),donationDto.getUnitMeasure(),donationDto.getExpirationDate());
            }
            Donation donation = new Donation(null, distributionCenter, quantity, optionalFoods.get(), null, null);
            donationRepository.save(donation);
        }

    }

    public void saveToiletriesDonation(ToiletriesDto toiletriesDto, DistributionCenter distributionCenter, Integer quantity){
        if(canDistributionCenterReceiveItemOfType("higiene", quantity)) {
            Optional<Toiletries> optionalToiletries = toiletriesRepository.findByValues(toiletriesDto.getDescription(), ToiletriesType.valueOf(toiletriesDto.getTypeToiletries().replace(" ", "_").toUpperCase()));
            if (optionalToiletries.isEmpty()) {
                Toiletries toiletries = new Toiletries(null, toiletriesDto.getDescription(), ToiletriesType.valueOf(toiletriesDto.getTypeToiletries().replace(" ", "_").toUpperCase()));
                toiletriesRepository.save(toiletries);
                optionalToiletries = toiletriesRepository.findByValues(toiletriesDto.getDescription(), ToiletriesType.valueOf(toiletriesDto.getTypeToiletries().replace(" ", "_").toUpperCase()));
            }
            Donation donation = new Donation(null, distributionCenter, quantity, null, null, optionalToiletries.get());
            donationRepository.save(donation);
        }

    }
}
