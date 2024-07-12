package com.compass.aidshelter.repositories;

import com.compass.aidshelter.entities.Foods;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FoodsRepository extends Repository<Foods> {

    public FoodsRepository() {
        super(Foods.class);
    }


    public Optional<Foods> findByValues(String description, Integer quantity, String unitMeasure, LocalDate expirationDate) {
        String jpql = "SELECT f FROM Foods f " +
                "WHERE f.description = :description " +
                "AND f.quantity = :quantity " +
                "AND f.unitMeasure = :unitMeasure " +
                "AND f.expirationDate = :expirationDate";

        TypedQuery<Foods> query = em.createQuery(jpql, Foods.class);
        query.setParameter("description", description);
        query.setParameter("quantity", quantity);
        query.setParameter("unitMeasure", unitMeasure);
        query.setParameter("expirationDate", expirationDate);

        List<Foods> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }
}
