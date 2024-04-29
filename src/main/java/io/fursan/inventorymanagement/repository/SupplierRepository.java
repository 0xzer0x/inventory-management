package io.fursan.inventorymanagement.repository;

import io.fursan.inventorymanagement.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {}
