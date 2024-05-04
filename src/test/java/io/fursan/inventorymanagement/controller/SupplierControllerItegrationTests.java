package io.fursan.inventorymanagement.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import io.fursan.inventorymanagement.dto.SupplierDto;
import io.fursan.inventorymanagement.entity.Supplier;
import io.fursan.inventorymanagement.service.SupplierService;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SupplierControllerItegrationTests {
  private MockMvc mockMvc;
  private SupplierService supplierService;

  @Autowired
  public SupplierControllerItegrationTests(MockMvc mockMvc, SupplierService supplierService) {
    this.mockMvc = mockMvc;
    this.supplierService = supplierService;
  }

  @Test
  @WithMockUser(
      username = "user",
      password = "user",
      roles = {"USER"})
  public void testThatGetSuppliersReturnsHttp200() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/suppliers"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithMockUser(
      username = "user",
      password = "user",
      roles = {"USER"})
  public void testThatGetSuppliersReturnsCorrectView() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/suppliers"))
        .andExpect(MockMvcResultMatchers.view().name("suppliers/list-suppliers"));
  }

  @Test
  @WithMockUser(
      username = "user",
      password = "user",
      roles = {"USER"})
  public void testThatGetSuppliersReturnsModelContainingItemsAttribute() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/suppliers"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("suppliers"));
  }

  @Test
  @WithMockUser(
      username = "user",
      password = "user",
      roles = {"USER"})
  public void testThatSaveSupplierIsNotAccessibleToUsers() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/suppliers/save"))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithMockUser(
      username = "employee",
      password = "employee",
      roles = {"EMPLOYEE"})
  public void testThatSaveSupplierIsAccessibleToEmployees() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/suppliers/save"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatSaveSupplierIsAccessibleToAdmins() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/suppliers/save"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatSaveSupplierReturnsCorrectView() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/suppliers/save"))
        .andExpect(MockMvcResultMatchers.view().name("suppliers/save-supplier"));
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatSaveSupplierReturnsModelContainingSupplierAttribute() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/suppliers/save"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("supplier"));
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatSaveSupplierNoParameterReturnsEmptySupplierDtoAttribute() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/suppliers/save"))
        .andExpect(MockMvcResultMatchers.model().attribute("supplier", new SupplierDto()));
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatSaveSupplierWithParameterReturnsCorrectSupplierDtoAttribute()
      throws Exception {
    Supplier supplier = supplierService.findById(1).get();
    ModelAndView modelAndView =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/suppliers/save").param("id", "1"))
            .andReturn()
            .getModelAndView();

    SupplierDto modelSupplierDto = (SupplierDto) modelAndView.getModel().get("supplier");
    Assertions.assertThat(modelSupplierDto.getId()).isEqualTo(supplier.getId());
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatSaveSupplierPersistsSupplierInDatabase() throws Exception {
    Supplier supplier =
        Supplier.builder()
            .id(1)
            .name("test")
            .email("test@test.com")
            .phoneNumber("+201001234567")
            .build();
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/suppliers/save")
                .with(csrf())
                .param("id", String.valueOf(supplier.getId()))
                .param("name", supplier.getName())
                .param("email", supplier.getEmail())
                .param("phoneNumber", supplier.getPhoneNumber()))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

    Supplier insertedSupplier = supplierService.findById(1).get();
    Assertions.assertThat(insertedSupplier.getName()).isEqualTo(supplier.getName());
    Assertions.assertThat(insertedSupplier.getEmail()).isEqualTo(supplier.getEmail());
    Assertions.assertThat(insertedSupplier.getPhoneNumber()).isEqualTo(supplier.getPhoneNumber());
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatDeleteSupplierRemovesSupplierFromDatabase() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/suppliers/delete").param("id", "1"))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

    Optional<Supplier> removedSupplier = supplierService.findById(1);
    Assertions.assertThat(removedSupplier).isEmpty();
  }
}
