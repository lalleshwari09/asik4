package bibo.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bibo.demo.Item;
import bibo.demo.repository.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void addItem(Item item) {
        itemRepository.addItem(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.getAllItems();
    }

    public Item getItemById(int id) {
        return itemRepository.getItem(id);
    }

    public void deleteItem(int id) {
        itemRepository.deleteItem(id);
    }

    public void updateItem(int id, Item updatedItem) {
        itemRepository.updateItem(id, updatedItem);
    }
}
