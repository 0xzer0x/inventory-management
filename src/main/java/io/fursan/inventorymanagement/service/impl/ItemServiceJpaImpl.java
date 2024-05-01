package io.fursan.inventorymanagement.service.impl;

import io.fursan.inventorymanagement.entity.Item;
import io.fursan.inventorymanagement.entity.Supplier;
import io.fursan.inventorymanagement.repository.ItemRepository;
import io.fursan.inventorymanagement.service.ItemService;
import io.fursan.inventorymanagement.service.SupplierService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceJpaImpl implements ItemService {
  private ItemRepository itemRepository;
  private SupplierService supplierService;

  public ItemServiceJpaImpl(ItemRepository itemRepository, SupplierService supplierService) {
    this.itemRepository = itemRepository;
    this.supplierService = supplierService;
  }

  @Override
  public List<Item> findAll() {
    return itemRepository.findAll();
  }

  @Override
  public List<Item> findAllBySuppliersContaining(Supplier supplier) {
    return itemRepository.findAllBySuppliersContaining(supplier);
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
    itemRepository
        .findById(id)
        .ifPresent(
            item -> {
              List<Supplier> suppliers = supplierService.findAllByItemsContaining(item);
              suppliers.forEach(itemSupplier -> itemSupplier.getItems().remove(item));
              itemRepository.deleteById(id);
            });
  }
}
