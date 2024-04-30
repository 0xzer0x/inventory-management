package io.fursan.inventorymanagement.controller;

import io.fursan.inventorymanagement.dto.ItemDto;
import io.fursan.inventorymanagement.dto.SupplierDto;
import io.fursan.inventorymanagement.mapper.impl.SupplierMapper;
import io.fursan.inventorymanagement.service.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    private SupplierService supplierService;
    private SupplierMapper supplierMapper;

    public SupplierController(SupplierService supplierService,SupplierMapper supplierMapper) {
        this.supplierService=supplierService;
        this.supplierMapper=supplierMapper;
    }

    @GetMapping
    public String getSuppliers(Model model){
        List<SupplierDto> supplierDtos = supplierService.findAll().stream().map(supplierMapper::mapTo).toList();
        model.addAttribute("suppliers", supplierDtos);
        return "suppliers/list-suppliers";

    }


}
