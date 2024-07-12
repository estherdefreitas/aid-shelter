package com.compass.aidshelter.dto;

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
    //record
}
