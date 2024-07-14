package com.compass.aidshelter.services;

import com.compass.aidshelter.dto.ShelterDto;
import com.compass.aidshelter.repositories.ShelterRepository;
import jakarta.persistence.EntityExistsException;

public class ShelterService {

    private ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public void saveShelter(ShelterDto shelterDto){
        if(!shelterRepository.findShelterByValues(shelterDto.name(),shelterDto.address(),shelterDto.responsible(),shelterDto.phone(),shelterDto.email(),shelterDto.storageCapacity())){
            shelterRepository.save(shelterDto.toEntity(null));
            } else {
                throw new EntityExistsException("Já existe um abrigo com esses dados. ");
            }

    }
}
