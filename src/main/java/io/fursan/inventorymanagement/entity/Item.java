package io.fursan.inventorymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "item")
public class Item {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "quantity")
  private Long quantity;

  @Column(name = "unit_price")
  private Double unitPrice;

  @ManyToMany(mappedBy = "items")
  private List<Supplier> supplier;
}
