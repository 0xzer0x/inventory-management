package io.fursan.inventorymanagement.controller;

import io.fursan.inventorymanagement.dto.ItemDto;
import io.fursan.inventorymanagement.mapper.impl.ItemMapper;
import io.fursan.inventorymanagement.service.ItemService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

  @GetMapping("/save")
  public String showSaveItemForm(
      @RequestParam(required = false) Optional<Integer> id, Model model) {
    model.addAttribute(
        "item",
        id.map(itemId -> itemService.findById(itemId).orElse(null))
            .map(itemMapper::mapTo)
            .orElse(new ItemDto()));
    return "items/save-item";
  }

  @PostMapping("/save")
  public String saveItem(
      @Valid @ModelAttribute(name = "item") ItemDto itemDto, BindingResult result) {
    if (result.hasErrors()) {
      return "items/save-item";
    }

    itemService.save(itemMapper.mapFrom(itemDto));
    return "redirect:/items";
  }

  @GetMapping("/delete")
    public String deleteById(@RequestParam(name="id") Integer id){
        itemService.deleteById(id);
        return "redirect:/items";
    }
}
