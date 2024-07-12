package com.compass.aidshelter.repositories;


import com.compass.aidshelter.entities.Toiletries;

public class ToiletriesRepository extends Repository<Toiletries, String> implements ItemRepository{
    public ToiletriesRepository() {
        super(Toiletries.class);
    }

//    @Override
//    public int getQuantityInDistributionCenter(Long distributionCenterId, Long itemId) {
//        Long count = (Long) em.createQuery("SELECT COUNT(t) FROM Toiletries t WHERE t.distributionCenter.id = :distributionCenterId AND t.id = :itemId")
//                .setParameter("distributionCenterId", distributionCenterId)
//                .setParameter("itemId", itemId)
//                .getSingleResult();
//        return Math.toIntExact(count);
//    }
}
