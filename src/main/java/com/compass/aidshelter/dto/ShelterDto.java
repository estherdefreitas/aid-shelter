package com.compass.aidshelter.dto;

import com.compass.aidshelter.entities.Shelter;

public record ShelterDto (
        String name,
        String address,
        String responsible,
        String phone,
        String email,
        String storageCapacity
){
    public Shelter toEntity(Long id) {
        return new Shelter(id, name, address, responsible, phone, email, storageCapacity);
    }
}
