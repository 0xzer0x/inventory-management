package io.fursan.inventorymanagement.mapper.impl;

import io.fursan.inventorymanagement.dto.SupplierDto;
import io.fursan.inventorymanagement.entity.Supplier;
import io.fursan.inventorymanagement.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper implements Mapper<Supplier, SupplierDto> {
  ModelMapper modelMapper;

  public SupplierMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Override
  public SupplierDto mapTo(Supplier source) {
    return modelMapper.map(source, SupplierDto.class);
  }

  @Override
  public Supplier mapFrom(SupplierDto target) {
    return modelMapper.map(target, Supplier.class);
  }
}
