package com.compass.aidshelter.dto;

import com.compass.aidshelter.entities.Item;
import com.compass.aidshelter.validators.ValidItemType;

public record OrderItemDto (
        @ValidItemType
        String itemType,
        int quantity,
        Item item
        ){
    //record
}
