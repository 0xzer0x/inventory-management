package io.fursan.inventorymanagement.service;

import io.fursan.inventorymanagement.entity.Item;
import io.fursan.inventorymanagement.entity.Supplier;
import java.util.List;
import java.util.Optional;

public interface ItemService {
  List<Item> findAll();

  List<Item> findAllBySuppliersContaining(Supplier supplier);

  Optional<Item> findById(int id);

  boolean existsById(int id);

  Item save(Item item);

  Item update(int id, Item item);

  void deleteById(int id);
}
