package com.compass.aidshelter.repositories;


import com.compass.aidshelter.entities.Toiletries;
import com.compass.aidshelter.entities.enums.ToiletriesType;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ToiletriesRepository extends Repository<Toiletries> {
    public ToiletriesRepository() {
        super(Toiletries.class);
    }

    public Optional<Toiletries> findByValues(String description, ToiletriesType toiletriesType) {
        String jpql = "SELECT t FROM Toiletries t " +
                "WHERE t.description = :description " +
                "AND t.type = :toiletriesType " ;

        TypedQuery<Toiletries> query = em.createQuery(jpql, Toiletries.class);
        query.setParameter("description", description);
        query.setParameter("toiletriesType", toiletriesType);

        List<Toiletries> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }
}
