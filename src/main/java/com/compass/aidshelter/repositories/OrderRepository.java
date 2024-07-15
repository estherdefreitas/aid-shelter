package com.compass.aidshelter.repositories;

import com.compass.aidshelter.entities.Order;

public class OrderRepository extends Repository<Order> {
    public OrderRepository() {
        super(Order.class);
    }

}
