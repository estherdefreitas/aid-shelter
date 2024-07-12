package com.compass.aidshelter.repositories;

import com.compass.aidshelter.entities.Foods;

public class FoodsRepository extends Repository<Foods, String> implements ItemRepository{

    public FoodsRepository() {
        super(Foods.class);
    }

//    @Override
//    public int getQuantityInDistributionCenter(Long distributionCenterId, Long itemId) {
//        Long count = (Long) em.createQuery("SELECT COUNT(f) FROM Foods f WHERE f.distributionCenter.id = :distributionCenterId AND f.id = :itemId")
//                .setParameter("distributionCenterId", distributionCenterId)
//                .setParameter("itemId", itemId)
//                .getSingleResult();
//        return count.intValue();
//    }
}
