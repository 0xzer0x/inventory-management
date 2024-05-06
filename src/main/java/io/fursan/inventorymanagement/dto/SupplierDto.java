package io.fursan.inventorymanagement.dto;

import jakarta.validation.constraints.Email;
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
public class SupplierDto {
  private Integer id;

  @NotBlank(message = "Name is required")
  private String name;

  @Email(message = "Invalid email address")
  private String email;

  @NotBlank(message = "Phone number cannot be empty")
  private String phoneNumber;

  private List<ItemDto> items;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    return this.id == ((SupplierDto) obj).getId();
  }

  @Override
  public String toString() {
    return "SupplierDto [id="
        + id
        + ", name="
        + name
        + ", email="
        + email
        + ", phoneNumber="
        + phoneNumber
        + "]";
  }
}
