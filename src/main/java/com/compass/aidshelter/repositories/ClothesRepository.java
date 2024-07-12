package com.compass.aidshelter.repositories;

import com.compass.aidshelter.entities.Clothes;
import com.compass.aidshelter.entities.enums.ClothesGender;
import com.compass.aidshelter.entities.enums.ClothesSize;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ClothesRepository extends Repository<Clothes> {

    public ClothesRepository() {
        super(Clothes.class);
    }

    public Optional<Clothes> findByValues(String description, ClothesSize size, ClothesGender gender){
        String jpql = "SELECT c FROM Clothes c " +
                "WHERE c.description = :description " +
                "AND c.size = :size " +
                "AND c.gender = :gender";

        TypedQuery<Clothes> query = em.createQuery(jpql, Clothes.class);
        query.setParameter("description", description);
        query.setParameter("size", size);
        query.setParameter("gender", gender);
        List<Clothes> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }
}
