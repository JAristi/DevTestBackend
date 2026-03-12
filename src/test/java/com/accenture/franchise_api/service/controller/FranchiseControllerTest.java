package com.accenture.franchise_api.service.controller;

import com.accenture.franchise_api.controller.FranchiseController;
import com.accenture.franchise_api.dto.TopProductDTO;
import com.accenture.franchise_api.entity.Branch;
import com.accenture.franchise_api.entity.Franchise;
import com.accenture.franchise_api.entity.Product;
import com.accenture.franchise_api.service.FranchiseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FranchiseController.class)
class FranchiseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FranchiseService franchiseService;

    @Autowired
    private ObjectMapper objectMapper;

    private Franchise franchise;
    private Branch branch;
    private Product product;
    private TopProductDTO topProduct;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Franchise Test");

        branch = new Branch();
        branch.setId(1L);
        branch.setName("Sucursal Test");
        branch.setFranchise(franchise);

        product = new Product();
        product.setId(1L);
        product.setName("Producto Test");
        product.setStock(100);
        product.setBranch(branch);

        topProduct = new TopProductDTO();
        topProduct.setBranchName("Sucursal Test");
        topProduct.setProductName("Producto Test");
        topProduct.setStock(100);
    }

    @Test
    @DisplayName("POST /api/franchises - Crear franquicia")
    void testCreateFranchise() throws Exception {
        // Given
        String name = "Nueva Franquicia";
        when(franchiseService.createFranchise(name)).thenReturn(franchise);

        // When & Then
        mockMvc.perform(post("/api/franchises")
                        .param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Franchise Test"));

        verify(franchiseService).createFranchise(name);
    }

    @Test
    @DisplayName("PUT /api/franchises/{id} - Actualizar nombre de franquicia")
    void testUpdateFranchise() throws Exception {
        // Given
        Long id = 1L;
        String newName = "Nuevo Nombre";
        Franchise updatedFranchise = new Franchise();
        updatedFranchise.setId(id);
        updatedFranchise.setName(newName);

        when(franchiseService.updateFranchiseName(id, newName)).thenReturn(updatedFranchise);

        // When & Then
        mockMvc.perform(put("/api/franchises/{id}", id)
                        .param("name", newName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName));

        verify(franchiseService).updateFranchiseName(id, newName);
    }

    @Test
    @DisplayName("POST /api/franchises/{franchiseId}/branches - Agregar sucursal")
    void testAddBranch() throws Exception {
        // Given
        Long franchiseId = 1L;
        String branchName = "Nueva Sucursal";

        when(franchiseService.addBranch(franchiseId, branchName)).thenReturn(branch);

        // When & Then
        mockMvc.perform(post("/api/franchises/{franchiseId}/branches", franchiseId)
                        .param("name", branchName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sucursal Test"));

        verify(franchiseService).addBranch(franchiseId, branchName);
    }

    @Test
    @DisplayName("PUT /api/franchises/branches/{id} - Actualizar sucursal")
    void testUpdateBranch() throws Exception {
        // Given
        Long branchId = 1L;
        String newName = "Sucursal Modificada";
        Branch updatedBranch = new Branch();
        updatedBranch.setId(branchId);
        updatedBranch.setName(newName);

        when(franchiseService.updateBranchName(branchId, newName)).thenReturn(updatedBranch);

        // When & Then
        mockMvc.perform(put("/api/franchises/branches/{id}", branchId)
                        .param("name", newName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(branchId))
                .andExpect(jsonPath("$.name").value(newName));

        verify(franchiseService).updateBranchName(branchId, newName);
    }

    @Test
    @DisplayName("POST /api/franchises/branches/{branchId}/products - Agregar producto")
    void testAddProduct() throws Exception {
        // Given
        Long branchId = 1L;
        String productName = "Nuevo Producto";
        Integer stock = 50;

        when(franchiseService.addProduct(branchId, productName, stock)).thenReturn(product);

        // When & Then
        mockMvc.perform(post("/api/franchises/branches/{branchId}/products", branchId)
                        .param("name", productName)
                        .param("stock", stock.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Producto Test"))
                .andExpect(jsonPath("$.stock").value(100));

        verify(franchiseService).addProduct(branchId, productName, stock);
    }

    @Test
    @DisplayName("PUT /api/franchises/products/{id} - Actualizar nombre de producto")
    void testUpdateProduct() throws Exception {
        // Given
        Long productId = 1L;
        String newName = "Producto Modificado";
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName(newName);
        updatedProduct.setStock(100);

        when(franchiseService.updateProductName(productId, newName)).thenReturn(updatedProduct);

        // When & Then
        mockMvc.perform(put("/api/franchises/products/{id}", productId)
                        .param("name", newName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.stock").value(100));

        verify(franchiseService).updateProductName(productId, newName);
    }

    @Test
    @DisplayName("PUT /api/franchises/products/{productId}/stock - Actualizar stock")
    void testUpdateStock() throws Exception {
        // Given
        Long productId = 1L;
        Integer newStock = 75;
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Producto Test");
        updatedProduct.setStock(newStock);

        when(franchiseService.updateStock(productId, newStock)).thenReturn(updatedProduct);

        // When & Then
        mockMvc.perform(put("/api/franchises/products/{productId}/stock", productId)
                        .param("stock", newStock.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.stock").value(newStock));

        verify(franchiseService).updateStock(productId, newStock);
    }

    @Test
    @DisplayName("DELETE /api/franchises/franchises/{franchiseId}/branches/{branchId}/products/{productId} - Eliminar producto")
    void testDeleteProduct() throws Exception {
        // Given
        Long franchiseId = 1L;
        Long branchId = 1L;
        Long productId = 1L;

        doNothing().when(franchiseService).deleteProduct(franchiseId, branchId, productId);

        // When & Then
        mockMvc.perform(delete("/api/franchises/franchises/{franchiseId}/branches/{branchId}/products/{productId}",
                        franchiseId, branchId, productId))
                .andExpect(status().isNoContent());

        verify(franchiseService).deleteProduct(franchiseId, branchId, productId);
    }

    @Test
    @DisplayName("GET /api/franchises/{franchiseId}/top-products - Obtener top productos")
    void testGetTopProducts() throws Exception {
        // Given
        Long franchiseId = 1L;
        List<TopProductDTO> topProducts = Arrays.asList(topProduct);

        when(franchiseService.getTopProducts(franchiseId)).thenReturn(topProducts);

        // When & Then
        mockMvc.perform(get("/api/franchises/{franchiseId}/top-products", franchiseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].branchName").value("Sucursal Test"))
                .andExpect(jsonPath("$[0].productName").value("Producto Test"))
                .andExpect(jsonPath("$[0].stock").value(100));

        verify(franchiseService).getTopProducts(franchiseId);
    }
}