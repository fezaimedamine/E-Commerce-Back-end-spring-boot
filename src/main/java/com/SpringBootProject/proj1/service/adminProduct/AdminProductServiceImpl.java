package com.SpringBootProject.proj1.service.adminProduct;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBootProject.proj1.Entitys.Category;
import com.SpringBootProject.proj1.Entitys.Product;
import com.SpringBootProject.proj1.Repositry.CategoryRepository;
import com.SpringBootProject.proj1.Repositry.ProductRepository;
import com.SpringBootProject.proj1.dto.ProductDto;

import io.jsonwebtoken.io.IOException;

@Service
public class AdminProductServiceImpl implements AdminProductService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        
        if (productDto.getImg() != null && !productDto.getImg().isEmpty()) {
            product.setImg(productDto.getByteImg());
        }

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return savedProduct.getDto();
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> list = productRepository.findAll();
        return list.stream()
                   .map(Product::getDto)
                   .collect(Collectors.toList());
    }
    @Override
    public List<ProductDto> getAllProductByName(String name) {
        List<Product> list = productRepository.findAllByNameContaining(name);
        return list.stream()
                   .map(Product::getDto)
                   .collect(Collectors.toList());
    }
    @Override
    public boolean deleteProduct(Long id){
        Optional<Product> optionalProduct=productRepository.findById(id);
        if(optionalProduct.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
        return optionalProduct.get().getDto();
        }else{
        return null;
        }
        }
        
        public ProductDto updateProduct (Long productId, ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(optionalProduct.isPresent() && optionalCategory.isPresent()){
        Product product = optionalProduct.get();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription (productDto.getDescription());
        product.setCategory (optionalCategory.get());
        if(productDto.getImg() != null) {
            product.setImg(productDto.getByteImg());
        }
        return productRepository.save(product).getDto();
        }else{
            return null;
        }
        
        }
}
