package io.fursan.inventorymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supplier")
public class Supplier {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "phone_number")
  private String phoneNumber;

  @ManyToMany private List<Item> items;

  @Override
  public String toString() {
    return "Supplier [id="
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
