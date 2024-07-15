package com.compass.aidshelter.repositories;

import com.compass.aidshelter.entities.Item;

import java.util.List;

public class ItemRepository extends Repository<Item> {

    public ItemRepository() {
        super(Item.class);
    }

    public List<Item> findAllItemsOfType(String itemType) {
        return em.createQuery("SELECT i FROM Item i WHERE TYPE(i) = :itemType", Item.class)
                .setParameter("itemType", itemType)
                .getResultList();
    }
}
