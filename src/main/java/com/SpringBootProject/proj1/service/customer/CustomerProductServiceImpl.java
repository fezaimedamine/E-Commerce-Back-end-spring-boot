package com.SpringBootProject.proj1.service.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBootProject.proj1.Entitys.Product;
import com.SpringBootProject.proj1.Repositry.ProductRepository;
import com.SpringBootProject.proj1.dto.ProductDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService{

    @Autowired
    private final ProductRepository  productRepository;
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> list = productRepository.findAll();
        return list.stream()
                   .map(Product::getDto)
                   .collect(Collectors.toList());
    }
    @Override
    public List<ProductDto> searchProductByTitle(String name) {
        List<Product> list = productRepository.findAllByNameContaining(name);
        return list.stream()
                   .map(Product::getDto)
                   .collect(Collectors.toList());
    }
}
