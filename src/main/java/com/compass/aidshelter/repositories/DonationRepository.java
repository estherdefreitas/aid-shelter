package com.compass.aidshelter.repositories;

import com.compass.aidshelter.entities.Donation;

public class DonationRepository extends Repository<Donation> {
    public DonationRepository() {
        super(Donation.class);
    }

    public Integer countAllDonations() {
        return em.createQuery("SELECT COUNT(d) FROM Donation d", Integer.class)
                .getSingleResult();
    }

    public Long countAllDonationsByType(String type) {
        Long count = switch (type) {
            case "roupa" ->
                    em.createQuery("SELECT SUM(d.quantity) FROM Donation d WHERE d.clothes is not null", Long.class)
                            .getSingleResult();
            case "comida" ->
                    em.createQuery("SELECT SUM(d.quantity) FROM Donation d WHERE d.foods is not null", Long.class)
                            .getSingleResult();
            case "higiene" ->
                    em.createQuery("SELECT SUM(d.quantity) FROM Donation d WHERE d.toiletries is not null", Long.class)
                            .getSingleResult();
            default -> -1L;
        };

        return count != null ? count : 0;
    }

    public Long countAllItemDonationsByDistributionCenterId(Long distributionCenterId, Long itemId) {
        return em.createQuery(
                """
                        SELECT COUNT(d.quantity)
                        FROM Donation d
                        WHERE d.distributionCenter.id = :distributionCenterId
                        AND ((d.toiletries is not null AND d.toiletries.id = :itemId)
                        OR (d.clothes is not null AND d.clothes.id = :itemId)
                        OR (d.foods is not null AND d.foods.id = :itemId))
                        """,
                        Long.class
                )
                .setParameter("distributionCenterId", distributionCenterId)
                .setParameter("itemId", itemId)
                .getSingleResult();
    }

}
