package io.fursan.inventorymanagement.dto;

import io.fursan.inventorymanagement.entity.Supplier;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
  private Integer id;

  private String name;

  private Long quantity;

  private Double unitPrice;

  private List<Supplier> supplier;
}
