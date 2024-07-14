package com.compass.aidshelter.services;

import com.compass.aidshelter.dto.ShelterDto;
import com.compass.aidshelter.entities.Shelter;
import com.compass.aidshelter.repositories.ShelterRepository;
import jakarta.persistence.EntityExistsException;

import java.util.Optional;

public class ShelterService {

    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public void saveShelter(ShelterDto shelterDto){
        Optional<Shelter> optionalShelter = shelterRepository.findShelterByValues(shelterDto.name(),shelterDto.address(),shelterDto.responsible(),shelterDto.phone(),shelterDto.email(),shelterDto.occupationCapacity(),shelterDto.occupationPercentage());
        if(optionalShelter.isEmpty()){
            shelterRepository.save(shelterDto.toEntity(null));
        } else {
            throw new EntityExistsException("Já existe um abrigo com esses dados. ");
        }
    }
}
