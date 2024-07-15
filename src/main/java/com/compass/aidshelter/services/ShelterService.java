package com.compass.aidshelter.services;

import com.compass.aidshelter.dto.ShelterDto;
import com.compass.aidshelter.entities.OrderItem;
import com.compass.aidshelter.entities.Shelter;
import com.compass.aidshelter.repositories.OrderItemRepository;
import com.compass.aidshelter.repositories.ShelterRepository;
import jakarta.persistence.EntityExistsException;

import java.util.List;
import java.util.Optional;

public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final OrderItemRepository orderItemRepository;

    public ShelterService(ShelterRepository shelterRepository, OrderItemRepository orderItemRepository) {
        this.shelterRepository = shelterRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public void saveShelter(ShelterDto shelterDto){
        Optional<Shelter> optionalShelter = shelterRepository.findShelterByValues(shelterDto.name(),shelterDto.address(),shelterDto.responsible(),shelterDto.phone(),shelterDto.email(),shelterDto.occupationCapacity(),shelterDto.occupationPercentage());
        if(optionalShelter.isEmpty()){
            shelterRepository.save(shelterDto.toEntity(null));
        } else {
            throw new EntityExistsException("Já existe um abrigo com esses dados. ");
        }
    }

    public void updateShelter(Long id, ShelterDto shelterDto) {
        Optional<Shelter> optionalShelter = Optional.ofNullable(shelterRepository.findById(id));
        if (optionalShelter.isPresent()) {
            Shelter shelter = optionalShelter.get();
            shelter.setName(shelterDto.name());
            shelter.setAddress(shelterDto.address());
            shelter.setResponsible(shelterDto.responsible());
            shelter.setPhone(shelterDto.phone());
            shelter.setEmail(shelterDto.email());
            shelter.setStorageCapacity(shelterDto.occupationCapacity());
            shelter.setOccupationPercentage(shelterDto.occupationPercentage());
            shelterRepository.save(shelter);
        } else {
            throw new EntityExistsException("Abrigo não encontrado. ");
        }
    }

    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> findOrderItemByShelterId(Long shelterId) {
        return orderItemRepository.findByShelterId(shelterId);
    }
    public List<OrderItem> findAllOrderItems() {
        return orderItemRepository.findAll();
    }
}
