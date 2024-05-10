package io.fursan.inventorymanagement.service;

import io.fursan.inventorymanagement.entity.Item;
import io.fursan.inventorymanagement.entity.Supplier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
  List<Item> findAll();

  Page<Item> findAll(Pageable pageable);

  List<Item> findAllBySuppliersContaining(Supplier supplier);

  Optional<Item> findById(int id);

  boolean existsById(int id);

  Item save(Item item);

  Item update(int id, Item item);

  void deleteById(int id);
}
