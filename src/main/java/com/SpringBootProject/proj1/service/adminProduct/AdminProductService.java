package com.SpringBootProject.proj1.service.adminProduct;

import java.util.List;

import com.SpringBootProject.proj1.dto.ProductDto;

public interface AdminProductService {
    ProductDto addProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    public List<ProductDto> getAllProductByName(String name);
    boolean deleteProduct(Long id);
    ProductDto updateProduct (Long productId, ProductDto productDto);
    ProductDto getProductById(Long productId);
}
