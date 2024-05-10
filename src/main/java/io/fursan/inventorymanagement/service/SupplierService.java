package io.fursan.inventorymanagement.service;

import io.fursan.inventorymanagement.entity.Item;
import io.fursan.inventorymanagement.entity.Supplier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService {
  List<Supplier> findAll();

  Page<Supplier> findAll(Pageable pageable);

  List<Supplier> findAllByItemsContaining(Item item);

  Optional<Supplier> findById(int id);

  boolean existsById(int id);

  Supplier save(Supplier supplier);

  Supplier update(int id, Supplier supplier);

  void deleteById(int id);
}
