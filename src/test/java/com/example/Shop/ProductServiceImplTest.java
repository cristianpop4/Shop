package com.example.Shop;

import com.example.Shop.dto.ProductDTO;
import com.example.Shop.entity.Product;
import com.example.Shop.repository.ProductRepository;
import com.example.Shop.service.serviceimpl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    private Product product;
    private ProductDTO dto;

    @BeforeEach
    void setUp() {

        product = new Product(
                1L,
                "Laptop",
                "Gaming laptop",
                5000.0,
                10,
                null
        );

        dto = new ProductDTO(
                1L,
                "Laptop",
                "Gaming laptop",
                5000.0,
                10
        );
    }

    @Test
    void save_ShouldReturnSavedProduct() {

        when(repository.save(any(Product.class))).thenReturn(product);

        ProductDTO saved = productServiceImpl.save(dto);

        assertNotNull(saved);
        assertEquals("Laptop", saved.name());

        verify(repository).save(any(Product.class));
    }

    @Test
    void findAll_ShouldReturnProducts() {

        when(repository.findAll()).thenReturn(List.of(product));

        List<ProductDTO> products = productServiceImpl.findAll();

        assertEquals(1, products.size());

        verify(repository).findAll();
    }

    @Test
    void findById_ShouldReturnProduct_WhenExists() {

        when(repository.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO found = productServiceImpl.findById(1L);

        assertEquals("Laptop", found.name());

        verify(repository).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenMissing() {

        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> productServiceImpl.findById(2L));
    }

    @Test
    void deleteById_ShouldCallRepository() {

        doNothing().when(repository).deleteById(1L);

        productServiceImpl.deleteById(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void update_ShouldModifyProduct() {

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(product)).thenReturn(product);

        ProductDTO updated = productServiceImpl.update(1L, dto);

        assertEquals("Laptop", updated.name());

        verify(repository).save(product);
    }

    @Test
    void findExpensiveProducts_ShouldReturnList() {

        when(repository.findByPriceGreaterThan(4000.0))
                .thenReturn(List.of(product));

        List<ProductDTO> result =
                productServiceImpl.findExpensiveProducts(4000.0);

        assertFalse(result.isEmpty());

        verify(repository).findByPriceGreaterThan(4000.0);
    }

    @Test
    void findLowStock_ShouldReturnProducts() {

        when(repository.findLowStock(20))
                .thenReturn(List.of(product));

        List<ProductDTO> result =
                productServiceImpl.findLowStock(20);

        assertEquals(1, result.size());

        verify(repository).findLowStock(20);
    }

    @Test
    void searchByName_ShouldReturnProducts() {

        when(repository.searchByNameNative("Lap"))
                .thenReturn(List.of(product));

        List<ProductDTO> result =
                productServiceImpl.searchByName("Lap");

        assertEquals("Laptop", result.get(0).name());

        verify(repository).searchByNameNative("Lap");
    }
}