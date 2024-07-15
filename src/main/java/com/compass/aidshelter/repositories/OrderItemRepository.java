package com.compass.aidshelter.repositories;

import com.compass.aidshelter.entities.OrderItem;

import java.util.List;

public class OrderItemRepository extends Repository<OrderItem> {
    public OrderItemRepository() {
        super(OrderItem.class);
    }

    public List<OrderItem> findByShelterId(Long shelterId) {
        return em.createQuery("SELECT o FROM OrderItem o WHERE o.shelter.id = :shelterId", OrderItem.class)
                .setParameter("shelterId", shelterId)
                .getResultList();
    }
}
