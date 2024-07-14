package com.compass.aidshelter.dto;

import com.compass.aidshelter.entities.*;
import com.compass.aidshelter.entities.enums.ClothesGender;
import com.compass.aidshelter.entities.enums.ClothesSize;
import com.compass.aidshelter.entities.enums.ToiletriesType;

import java.time.LocalDate;

public record DonationDto (
        String destination,
        String itemType,
        String description,
        Integer quantityItem,
        String size,
        String gender,
        String unitMeasure,
        Integer quantityFood,
        LocalDate expirationDate,
        String typeToiletries)
{
    public Donation toEntity() {
        Donation donation = new Donation();
        DistributionCenter distributionCenter = new DistributionCenter();
        distributionCenter.setId(Long.valueOf(destination));
        donation.setDistributionCenter(distributionCenter);
        donation.setQuantity(quantityItem);
        if(itemType.equals("roupa")){
            donation.setClothes(
                    new Clothes(null, description, ClothesSize.valueOf(size), ClothesGender.valueOf(gender))
            );
        } else if (itemType.equals("comida")) {
            donation.setFoods(
                    new Foods(null, description, quantityFood, unitMeasure, expirationDate)
            );
        } else if (itemType.equals("higiene")) {
            donation.setToiletries(
                    new Toiletries(null, description, ToiletriesType.valueOf(typeToiletries.replace(" ", "_").toUpperCase()))
            );
        }
        return donation;
    }

    public ClothesDto toClothesDto() {
        return new ClothesDto(description, size, gender);
    }

    public FoodsDto toFoodsDto() {
        return new FoodsDto(description, quantityFood, unitMeasure, expirationDate);
    }
    public ToiletriesDto toToiletriesDto() {
        return new ToiletriesDto(description, typeToiletries);
    }
}
