package io.fursan.inventorymanagement.service.impl;

import io.fursan.inventorymanagement.repository.SupplierRepository;
import io.fursan.inventorymanagement.service.SupplierService;
import io.fursan.inventorymanagement.entity.Supplier;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceJpaImpl implements SupplierService {
  private SupplierRepository supplierRepository;

  public SupplierServiceJpaImpl(SupplierRepository supplierRepository) {
    this.supplierRepository = supplierRepository;
  }

  @Override
  public List<Supplier> findAll() {
    return supplierRepository.findAll();
  }

  @Override
  public Optional<Supplier> findById(int id) {
    return supplierRepository.findById(id);
  }

  @Override
  public boolean existsById(int id) {
    return supplierRepository.existsById(id);
  }

  @Override
  public Supplier save(Supplier supplier) {
    return supplierRepository.save(supplier);
  }

  @Override
  public Supplier update(int id, Supplier supplier) {
    return supplierRepository
        .findById(id)
        .map(
            existingItem -> {
              Optional.ofNullable(supplier.getName()).ifPresent(name -> existingItem.setName(name));
              Optional.ofNullable(supplier.getEmail())
                  .ifPresent(email -> existingItem.setEmail(email));
              Optional.ofNullable(supplier.getPhoneNumber())
                  .ifPresent(phonenumber -> existingItem.setPhoneNumber(phonenumber));
              return existingItem;
            })
        .orElseThrow(() -> new RuntimeException("Id '" + id + "' does not exist"));
  }

  @Override
  public void deleteById(int id) {
    supplierRepository.deleteById(id);
  }


}
