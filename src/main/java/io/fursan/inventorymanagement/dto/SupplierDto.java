package io.fursan.inventorymanagement.dto;

import io.fursan.inventorymanagement.entity.Item;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {
  private Integer id;

  private String name;

  private String email;

  private String phoneNumber;

  private List<Item> items;
}
