package io.fursan.inventorymanagement.controller;

import io.fursan.inventorymanagement.dto.ItemDto;
import io.fursan.inventorymanagement.dto.SupplierDto;
import io.fursan.inventorymanagement.mapper.impl.ItemMapper;
import io.fursan.inventorymanagement.mapper.impl.SupplierMapper;
import io.fursan.inventorymanagement.service.ItemService;
import io.fursan.inventorymanagement.service.SupplierService;
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
@RequestMapping("/suppliers")
public class SupplierController {
  private SupplierMapper supplierMapper;
  private SupplierService supplierService;
  private ItemService itemService;
  private ItemMapper itemMapper;

  public SupplierController(
      SupplierMapper supplierMapper,
      SupplierService supplierService,
      ItemService itemService,
      ItemMapper itemMapper) {
    this.supplierMapper = supplierMapper;
    this.supplierService = supplierService;
    this.itemService = itemService;
    this.itemMapper = itemMapper;
  }

  private List<ItemDto> itemDtos() {
    return itemService.findAll().stream().map(itemMapper::mapTo).toList();
  }

  @GetMapping
  public String getSuppliers(Model model) {
    List<SupplierDto> supplierDtos =
        supplierService.findAll().stream().map(supplierMapper::mapTo).toList();
    model.addAttribute("suppliers", supplierDtos);
    return "suppliers/list-suppliers";
  }

  @GetMapping("/save")
  public String showSupplierSaveForm(
      @RequestParam(required = false, name = "id") Optional<Integer> id, Model model) {
    model.addAttribute("items", itemDtos());
    model.addAttribute(
        "supplier",
        id.map(supplierId -> supplierService.findById(supplierId).orElse(null))
            .map(supplierMapper::mapTo)
            .orElse(new SupplierDto()));
    return "suppliers/save-supplier";
  }

  @PostMapping("/save")
  public String saveSupplier(
      @Valid @ModelAttribute(name = "supplier") SupplierDto supplierDto,
      BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("items", itemDtos());
      return "suppliers/save-supplier";
    }

    supplierService.save(supplierMapper.mapFrom(supplierDto));
    return "redirect:/suppliers";
  }

  @GetMapping("/delete")
  public String deleteById(@RequestParam(name = "id") Integer id) {
    supplierService.deleteById(id);
    return "redirect:/suppliers";
  }
}
