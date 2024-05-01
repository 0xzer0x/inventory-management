package io.fursan.inventorymanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
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

  private List<SupplierDto> suppliers;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    return this.id == ((ItemDto) obj).getId();
  }

  @Override
  public String toString() {
    return "ItemDto [id="
        + id
        + ", name="
        + name
        + ", quantity="
        + quantity
        + ", unitPrice="
        + unitPrice
        + "]";
  }
}
