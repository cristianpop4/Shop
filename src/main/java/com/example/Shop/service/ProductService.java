package com.example.Shop.service;

import com.example.Shop.dto.ProductDTO;
import com.example.Shop.entity.Product;

import java.util.List;

public interface ProductService {

    default ProductDTO toDto(Product product){
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

    ProductDTO save(ProductDTO productDTO);
    List<ProductDTO> findAll();
    ProductDTO findById(Long id);
    void deleteById(Long id);
    ProductDTO update(Long id, ProductDTO productDTO);
    List<ProductDTO> findExpensiveProducts(Double price);
    List<ProductDTO> findLowStock(Integer stock);
    List<ProductDTO> searchByName(String name);
}
