package com.compass.aidshelter.dtos;

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
    LocalDate expirationDate)
{
    //record
}
