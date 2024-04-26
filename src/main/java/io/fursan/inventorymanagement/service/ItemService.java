package io.fursan.inventorymanagement.service;

import io.fursan.inventorymanagement.entity.Item;
import java.util.List;
import java.util.Optional;

public interface ItemService {
  List<Item> findAll();

  Optional<Item> findById(int id);

  boolean existsById(int id);

  Item save(Item item);

  Item update(int id, Item item);

  void deleteById(int id);
}
