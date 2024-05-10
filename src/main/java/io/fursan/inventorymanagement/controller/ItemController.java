package io.fursan.inventorymanagement.controller;

import io.fursan.inventorymanagement.dto.ItemDto;
import io.fursan.inventorymanagement.entity.Item;
import io.fursan.inventorymanagement.mapper.impl.ItemMapper;
import io.fursan.inventorymanagement.service.ItemService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
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
  public String getAllItems(
      Model model,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "6") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort) {
    List<ItemDto> itemDtos;
    String sortField = sort[0];
    String sortDirection = sort[1];

    Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Order order = new Order(direction, sortField);

    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));
    Page<Item> pageItems;
    pageItems = itemService.findAll(pageable);
    itemDtos = pageItems.getContent().stream().map(itemMapper::mapTo).toList();

    model.addAttribute("items", itemDtos);
    model.addAttribute("currentPage", pageItems.getNumber() + 1);
    model.addAttribute("totalItems", pageItems.getTotalElements());
    model.addAttribute("totalPages", pageItems.getTotalPages());
    model.addAttribute("pageSize", size);
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDirection", sortDirection);
    model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

    return "items/list-items";
  }

  @GetMapping("/save")
  public String showSaveItemForm(
      @RequestParam(required = false, name = "id") Optional<Integer> id, Model model) {
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
  public String deleteById(@RequestParam(name = "id") Integer id) {
    itemService.deleteById(id);
    return "redirect:/items";
  }
}
