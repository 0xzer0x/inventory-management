package io.fursan.inventorymanagement.controller;

import io.fursan.inventorymanagement.dto.ItemDto;
import io.fursan.inventorymanagement.mapper.impl.ItemMapper;
import io.fursan.inventorymanagement.service.ItemService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemController {
  private ItemService itemService;
  private ItemMapper itemMapper;

  public ItemController(ItemService itemService, ItemMapper itemMapper) {
    this.itemService = itemService;
    this.itemMapper = itemMapper;
  }

  @GetMapping
  public String getAllItems(Model model) {
    List<ItemDto> itemDtos = itemService.findAll().stream().map(itemMapper::mapTo).toList();
    model.addAttribute("items", itemDtos);
    return "items/list-items";
  }
}