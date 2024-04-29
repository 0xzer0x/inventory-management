package io.fursan.inventorymanagement.service;

import io.fursan.inventorymanagement.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
  List<Supplier> findAll();

  Optional<Supplier> findById(int id);

  boolean existsById(int id);

  Supplier save(Supplier supplier);

  Supplier update(int id, Supplier supplier);

  void deleteById(int id);
}
