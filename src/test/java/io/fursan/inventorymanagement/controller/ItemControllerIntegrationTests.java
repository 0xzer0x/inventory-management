package io.fursan.inventorymanagement.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import io.fursan.inventorymanagement.dto.ItemDto;
import io.fursan.inventorymanagement.entity.Item;
import io.fursan.inventorymanagement.service.ItemService;
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
public class ItemControllerIntegrationTests {
  private MockMvc mockMvc;
  private ItemService itemService;
  private SupplierService supplierService;

  @Autowired
  public ItemControllerIntegrationTests(
      MockMvc mockMvc, ItemService itemService, SupplierService supplierService) {
    this.mockMvc = mockMvc;
    this.itemService = itemService;
    this.supplierService = supplierService;
  }

  @Test
  @WithMockUser(
      username = "user",
      password = "user",
      roles = {"USER"})
  public void testThatGetitemsReturnsHttp200() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/items"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithMockUser(
      username = "user",
      password = "user",
      roles = {"USER"})
  public void testThatGetitemsReturnsCorrectView() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/items"))
        .andExpect(MockMvcResultMatchers.view().name("items/list-items"));
  }

  @Test
  @WithMockUser(
      username = "user",
      password = "user",
      roles = {"USER"})
  public void testThatGetitemsReturnsModelContainingItemsAttribute() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/items"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("items"));
  }

  @Test
  @WithMockUser(
      username = "user",
      password = "user",
      roles = {"USER"})
  public void testThatItemsSaveIsNotAccessibleToUsers() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/items/save"))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithMockUser(
      username = "employee",
      password = "employee",
      roles = {"EMPLOYEE"})
  public void testThatItemsSaveIsAccessibleToEmployees() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/items/save"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatItemsSaveIsAccessibleToAdmins() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/items/save"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatItemsSaveReturnsCorrectView() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/items/save"))
        .andExpect(MockMvcResultMatchers.view().name("items/save-item"));
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatItemsSaveReturnsModelContainingItemAttribute() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/items/save"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("item"));
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatItemsSaveReturnsCorrectItem() throws Exception {
    // no parameter
    mockMvc
        .perform(MockMvcRequestBuilders.get("/items/save"))
        .andExpect(MockMvcResultMatchers.model().attribute("item", new ItemDto()));

    // with id parameter
    Item item = itemService.findById(1).get();
    ModelAndView modelAndView =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/items/save").param("id", "1"))
            .andReturn()
            .getModelAndView();

    Assertions.assertThat(((ItemDto) modelAndView.getModel().get("item")).getId())
        .isEqualTo(item.getId());
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatSavePersistsItemInDatabase() throws Exception {
    Item item = Item.builder().id(1).name("test").quantity(10L).unitPrice(200.5).build();
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/items/save")
                .with(csrf())
                .param("id", String.valueOf(item.getId()))
                .param("quantity", String.valueOf(item.getQuantity()))
                .param("unitPrice", String.valueOf(item.getUnitPrice()))
                .param("name", String.valueOf(item.getName())))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

    Item insertedItem = itemService.findById(1).get();
    Assertions.assertThat(insertedItem.getName()).isEqualTo(item.getName());
    Assertions.assertThat(insertedItem.getQuantity()).isEqualTo(item.getQuantity());
    Assertions.assertThat(insertedItem.getUnitPrice()).isEqualTo(item.getUnitPrice());
  }

  @Test
  @WithMockUser(
      username = "admin",
      password = "admin",
      roles = {"ADMIN"})
  public void testThatDeleteRemovesItemFromDatabase() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/items/delete").param("id", "1"))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    Optional<Item> removedItem = itemService.findById(1);
    Assertions.assertThat(removedItem).isEmpty();
  }
}
