package io.fursan.inventorymanagement.service.impl;

import io.fursan.inventorymanagement.entity.Item;
import io.fursan.inventorymanagement.repository.ItemRepository;
import io.fursan.inventorymanagement.service.ItemService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceJpaImpl implements ItemService {
  private ItemRepository itemRepository;

  public ItemServiceJpaImpl(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @Override
  public List<Item> findAll() {
    return itemRepository.findAll();
  }

  @Override
  public Optional<Item> findById(int id) {
    return itemRepository.findById(id);
  }

  @Override
  public boolean existsById(int id) {
    return itemRepository.existsById(id);
  }

  @Override
  public Item save(Item item) {
    return itemRepository.save(item);
  }

  @Override
  public Item update(int id, Item item) {
    return itemRepository
        .findById(id)
        .map(
            existingItem -> {
              Optional.ofNullable(item.getName()).ifPresent(name -> existingItem.setName(name));
              Optional.ofNullable(item.getQuantity())
                  .ifPresent(quantity -> existingItem.setQuantity(quantity));
              Optional.ofNullable(item.getUnitPrice())
                  .ifPresent(unitPrice -> existingItem.setUnitPrice(unitPrice));
              Optional.ofNullable(item.getSuppliers())
                  .ifPresent(suppliers -> existingItem.setSuppliers(suppliers));
              return existingItem;
            })
        .orElseThrow(() -> new RuntimeException("Id '" + id + "' does not exist"));
  }

  @Override
  public void deleteById(int id) {
    itemRepository.deleteById(id);
  }
}