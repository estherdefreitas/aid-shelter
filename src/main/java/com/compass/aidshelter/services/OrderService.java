package com.compass.aidshelter.services;

import com.compass.aidshelter.entities.*;
import com.compass.aidshelter.repositories.DistributionCenterRepository;
import com.compass.aidshelter.repositories.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private final OrderRepository orderRepository;
    private final DistributionCenterRepository distributionCenterRepository;
    private final DonationService donationService;

    public OrderService(OrderRepository orderRepository, DistributionCenterRepository distributionCenterRepository, DonationService donationService) {
        this.orderRepository = orderRepository;
        this.distributionCenterRepository = distributionCenterRepository;
        this.donationService = donationService;
    }

    public List<DistributionCenter> findCentersForItem(Item item, int quantity) {
        List<DistributionCenter> centers = distributionCenterRepository.findAll();
        return centers.stream()
                .filter(center -> donationService.getAvailableQuantityByCenter( item, center) > 0)
                .sorted((center1, center2) -> {
                    Long quantity1 = donationService.getAvailableQuantityByCenter(item, center1);
                    Long quantity2 = donationService.getAvailableQuantityByCenter( item, center2);
                    return Long.compare(quantity2, quantity1);
                })
                .collect(Collectors.toList());
    }

    public Order createOrderRequest(Shelter shelter, List<OrderItem> items) {
        Order order = new Order();
        order.setShelter(shelter);
        order.setOrderItems(items);
        order.setFulfilled(false);
        orderRepository.save(order);
        return order;
    }
}
