package com.compass.aidshelter.repositories;

import com.compass.aidshelter.entities.Shelter;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ShelterRepository extends Repository<Shelter>{
    public ShelterRepository() {
        super(Shelter.class);
    }

    public boolean findShelterByValues(String name, String address, String responsible, String phone, String email, String storageCapacity){
        try {
            TypedQuery<Shelter> query = em.createQuery(
                    "SELECT s FROM Shelter s WHERE s.name = :name AND s.address = :address AND s.responsible = :responsible AND s.phone = :phone AND s.email = :email AND s.storageCapacity = :storageCapacity",
                    Shelter.class
            );
            query.setParameter("name", name);
            query.setParameter("address", address);
            query.setParameter("responsible", responsible);
            query.setParameter("phone", phone);
            query.setParameter("email", email);
            query.setParameter("storageCapacity", storageCapacity);

            Shelter shelter = query.getSingleResult();
            return shelter != null;
        } catch (NoResultException e) {
            return false;
        } finally {
            em.close();
        }
    }
}
