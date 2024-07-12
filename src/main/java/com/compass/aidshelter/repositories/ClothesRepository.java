package com.compass.aidshelter.repositories;

import com.compass.aidshelter.entities.Clothes;

public class ClothesRepository extends Repository<Clothes, String> implements ItemRepository {

    public ClothesRepository() {
        super(Clothes.class);
    }

//    public int getQuantityInDistributionCenter(Long distributionCenterId, Long itemId) {
//        Long count = (Long) em.createQuery("SELECT COUNT(c) FROM Clothes c WHERE c.distributionCenter.id = :distributionCenterId AND c.id = :itemId")
//                .setParameter("distributionCenterId", distributionCenterId)
//                .setParameter("itemId", itemId)
//                .getSingleResult();
//        return count.intValue();
//    }


}
