package com.SpringBootProject.proj1.service.customer;

import java.util.List;

import com.SpringBootProject.proj1.dto.ProductDto;

public interface CustomerProductService {
    List<ProductDto> getAllProducts();
    List<ProductDto> searchProductByTitle(String name);
}
