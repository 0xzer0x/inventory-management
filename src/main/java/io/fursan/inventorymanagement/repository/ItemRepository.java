package io.fursan.inventorymanagement.repository;

import io.fursan.inventorymanagement.entity.Item;
import io.fursan.inventorymanagement.entity.Supplier;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
  List<Item> findAllBySuppliersContaining(Supplier supplier);
}
