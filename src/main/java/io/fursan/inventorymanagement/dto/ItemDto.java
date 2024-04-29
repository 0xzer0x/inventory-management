package io.fursan.inventorymanagement.dto;

import io.fursan.inventorymanagement.entity.Supplier;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

  @NotBlank(message = "Name is required")
  private String name;

  @Min(value = 0, message = "Quantity must be 0 or greater")
  private Long quantity;

  @Min(value = 0, message = "Price must be positive")
  private Double unitPrice;

  private List<Supplier> suppliers;
}
