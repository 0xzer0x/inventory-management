package io.fursan.inventorymanagement.dto;

import io.fursan.inventorymanagement.entity.Item;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @NotBlank(message = "Name is required")
  private String name;

  @Email private String email;

  @NotNull(message = "Phone number cannot be empty")
  private String phoneNumber;

  private List<Item> items;
}
