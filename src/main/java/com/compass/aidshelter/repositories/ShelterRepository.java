package com.compass.aidshelter.repositories;

import com.compass.aidshelter.entities.Shelter;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ShelterRepository extends Repository<Shelter>{
    public ShelterRepository() {
        super(Shelter.class);
    }

    public Optional<Shelter> findShelterByValues(String name, String address, String responsible, String phone, String email, String storageCapacity, String occupationPercentage){

        TypedQuery<Shelter> query = super.em.createQuery(
                "SELECT s FROM Shelter s WHERE s.name = :name AND s.address = :address AND s.responsible = :responsible AND s.phone = :phone AND s.email = :email AND s.storageCapacity = :storageCapacity AND s.occupationPercentage = :occupationPercentage",
                Shelter.class
        );
        query.setParameter("name", name);
        query.setParameter("address", address);
        query.setParameter("responsible", responsible);
        query.setParameter("phone", phone);
        query.setParameter("email", email);
        query.setParameter("storageCapacity", storageCapacity);
        query.setParameter("occupationPercentage", occupationPercentage);

        List<Shelter> resultList = query.getResultList();
        if(resultList.isEmpty()) {
            return Optional.empty();
        }else {
            return Optional.of(resultList.get(0));
        }
    }
}
