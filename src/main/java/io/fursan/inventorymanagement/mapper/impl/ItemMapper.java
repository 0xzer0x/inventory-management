package io.fursan.inventorymanagement.mapper.impl;

import io.fursan.inventorymanagement.dto.ItemDto;
import io.fursan.inventorymanagement.entity.Item;
import io.fursan.inventorymanagement.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper implements Mapper<Item, ItemDto> {
  ModelMapper modelMapper;

  public ItemMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Override
  public ItemDto mapTo(Item source) {
    return modelMapper.map(source, ItemDto.class);
  }

  @Override
  public Item mapFrom(ItemDto target) {
    return modelMapper.map(target, Item.class);
  }
}
