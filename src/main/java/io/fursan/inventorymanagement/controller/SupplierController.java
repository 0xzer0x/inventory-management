package io.fursan.inventorymanagement.controller;

import io.fursan.inventorymanagement.dto.ItemDto;
import io.fursan.inventorymanagement.dto.SupplierDto;
import io.fursan.inventorymanagement.entity.Supplier;
import io.fursan.inventorymanagement.mapper.impl.ItemMapper;
import io.fursan.inventorymanagement.mapper.impl.SupplierMapper;
import io.fursan.inventorymanagement.service.ItemService;
import io.fursan.inventorymanagement.service.SupplierService;
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
  public String getSuppliers(Model model,@RequestParam(defaultValue = "1") int page,
  @RequestParam(defaultValue = "6") int size,
  @RequestParam(defaultValue = "id,asc") String[] sort) {
    List<SupplierDto> supplierDtos ;
    String sortField = sort[0];
      String sortDirection = sort[1];
      
      Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
      Order order = new Order(direction, sortField);
      
      Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));
    Page<Supplier> pageSuppliers;
    pageSuppliers = supplierService.findAll(pageable);
    supplierDtos = pageSuppliers.getContent().stream().map(supplierMapper::mapTo).toList();
      
      model.addAttribute("suppliers", supplierDtos);
      model.addAttribute("currentPage", pageSuppliers.getNumber() + 1);
      model.addAttribute("totalItems", pageSuppliers.getTotalElements());
      model.addAttribute("totalPages", pageSuppliers.getTotalPages());
      model.addAttribute("pageSize", size);
      model.addAttribute("sortField", sortField);
      model.addAttribute("sortDirection", sortDirection);
      model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

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
