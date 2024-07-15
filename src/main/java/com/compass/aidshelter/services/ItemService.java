package com.compass.aidshelter.services;

import com.compass.aidshelter.repositories.ItemRepository;

public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void chooseItemOfType(String itemType) {

    }
}
