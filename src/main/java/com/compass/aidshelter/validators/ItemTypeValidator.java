package com.compass.aidshelter.validators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class ItemTypeValidator implements ConstraintValidator<ValidItemType, String> {

    private static final List<String> VALID_ITEM_TYPES = Arrays.asList("Clothes", "Toiletries", "Foods");

    @Override
    public boolean isValid(String itemType, ConstraintValidatorContext context) {
        if (itemType == null) {
            return false;
        }
        return VALID_ITEM_TYPES.contains(itemType);
    }
}
