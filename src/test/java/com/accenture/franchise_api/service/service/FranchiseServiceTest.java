package com.accenture.franchise_api.service.service;

import com.accenture.franchise_api.dto.TopProductDTO;
import com.accenture.franchise_api.entity.Branch;
import com.accenture.franchise_api.entity.Franchise;
import com.accenture.franchise_api.entity.Product;
import com.accenture.franchise_api.repository.BranchRepository;
import com.accenture.franchise_api.repository.FranchiseRepository;
import com.accenture.franchise_api.repository.ProductRepository;
import com.accenture.franchise_api.service.Impl.FranchiseServiceImpl;
import com.accenture.franchise_api.service.RedisCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RedisCacheService redisCacheService;

    @InjectMocks
    private FranchiseServiceImpl franchiseService;

    private Franchise franchiseNorte;
    private Branch branchMedellin;
    private Product hamburguesa;
    private Product pizza;

    @BeforeEach
    void setUp() {
        // Configurar franquicia
        franchiseNorte = new Franchise();
        franchiseNorte.setId(1L);
        franchiseNorte.setName("Franchise Norte");

        // Configurar sucursal
        branchMedellin = new Branch();
        branchMedellin.setId(1L);
        branchMedellin.setName("Sucursal Medellin");
        branchMedellin.setFranchise(franchiseNorte);

        // Configurar productos
        hamburguesa = new Product();
        hamburguesa.setId(1L);
        hamburguesa.setName("Hamburguesa");
        hamburguesa.setStock(50);
        hamburguesa.setBranch(branchMedellin);

        pizza = new Product();
        pizza.setId(2L);
        pizza.setName("Pizza");
        pizza.setStock(30);
        pizza.setBranch(branchMedellin);
    }

    @Test
    @DisplayName("Debe crear una franquicia correctamente")
    void testCreateFranchise() {
        // Given
        String name = "Nueva Franquicia";
        Franchise franchiseToSave = Franchise.builder().name(name).build();
        Franchise savedFranchise = Franchise.builder().id(3L).name(name).build();

        when(franchiseRepository.save(any(Franchise.class))).thenReturn(savedFranchise);

        // When
        Franchise result = franchiseService.createFranchise(name);

        // Then
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals(name, result.getName());
        verify(franchiseRepository).save(any(Franchise.class));
    }

    @Test
    @DisplayName("Debe agregar una sucursal a una franquicia existente")
    void testAddBranch() {
        // Given
        Long franchiseId = 1L;
        String branchName = "Nueva Sucursal";

        when(franchiseRepository.findById(franchiseId)).thenReturn(Optional.of(franchiseNorte));

        Branch branchToSave = Branch.builder()
                .name(branchName)
                .franchise(franchiseNorte)
                .build();

        Branch savedBranch = Branch.builder()
                .id(4L)
                .name(branchName)
                .franchise(franchiseNorte)
                .build();

        when(branchRepository.save(any(Branch.class))).thenReturn(savedBranch);

        // When
        Branch result = franchiseService.addBranch(franchiseId, branchName);

        // Then
        assertNotNull(result);
        assertEquals(4L, result.getId());
        assertEquals(branchName, result.getName());
        assertEquals(franchiseId, result.getFranchise().getId());
    }

    @Test
    @DisplayName("Debe obtener el top de productos por franquicia")
    void testGetTopProducts() {
        // Given
        Long franchiseId = 1L;
        List<Product> products = Arrays.asList(hamburguesa, pizza);

        when(productRepository.findTopStockProducts(franchiseId)).thenReturn(products);

        // When
        List<TopProductDTO> result = franchiseService.getTopProducts(franchiseId);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getProductName()).isEqualTo("Hamburguesa");
        assertThat(result.get(0).getStock()).isEqualTo(50);
        assertThat(result.get(1).getProductName()).isEqualTo("Pizza");
        assertThat(result.get(1).getStock()).isEqualTo(30);
    }

    @Test
    @DisplayName("Debe actualizar el stock y limpiar caché")
    void testUpdateStock() {
        // Given
        Long productId = 1L;
        Integer newStock = 75;

        when(productRepository.findById(productId)).thenReturn(Optional.of(hamburguesa));
        when(productRepository.save(any(Product.class))).thenReturn(hamburguesa);
        when(redisCacheService.delete(anyString())).thenReturn(Mono.just(true));

        // When
        Product result = franchiseService.updateStock(productId, newStock);

        // Then
        assertNotNull(result);
        assertEquals(newStock, result.getStock());
        verify(redisCacheService).delete(anyString());
    }
}