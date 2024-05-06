package io.fursan.inventorymanagement.utils;

import io.fursan.inventorymanagement.dto.ItemDto;
import io.fursan.inventorymanagement.mapper.impl.ItemMapper;
import io.fursan.inventorymanagement.repository.ItemRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemByIdConverter implements Converter<String, ItemDto> {
  private ItemRepository itemRepository;
  private ItemMapper itemMapper;

  public ItemByIdConverter(ItemRepository itemRepository, ItemMapper itemMapper) {
    this.itemRepository = itemRepository;
    this.itemMapper = itemMapper;
  }

  @Override
  public ItemDto convert(String source) {
    int id = Integer.parseInt(source);
    return itemRepository.findById(id).map(itemMapper::mapTo).orElse(null);
  }
}
