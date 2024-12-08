package com.SpringBootProject.proj1.Controller.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootProject.proj1.dto.FAQDto;
import com.SpringBootProject.proj1.dto.ProductDto;
import com.SpringBootProject.proj1.service.FAQ.FAQService;
import com.SpringBootProject.proj1.service.adminProduct.AdminProductService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;
    private final FAQService faqService;
    @PostMapping("/admin/product")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto product1Dto = adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product1Dto);
    }

    @GetMapping("/admin/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = adminProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }
    @GetMapping("/admin/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductByName(@PathVariable String name) {
        List<ProductDto> productDtos = adminProductService.getAllProductByName(name);
        return ResponseEntity.ok(productDtos);
    }
    @DeleteMapping("/admin/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        boolean deleted=adminProductService.deleteProduct(productId);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/faq/{productId}")
    public ResponseEntity<FAQDto> postFAQ(@PathVariable Long productId,@RequestBody FAQDto faqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(faqService.postFAQ(productId, faqDto));
    }

   @GetMapping("/product/{productId}")
   public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
    ProductDto productDto=adminProductService.getProductById(productId);
    if(productDto != null){
        return ResponseEntity.ok(productDto);
    }else{
        return ResponseEntity.notFound().build();
    }
   } 

   @PostMapping("/product/{productId}")
   public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId,@ModelAttribute ProductDto productDto) throws IOException{
    ProductDto updateProduct=adminProductService.updateProduct(productId,productDto);
    if(updateProduct != null){
        return ResponseEntity.ok(updateProduct);
    }else{
        return ResponseEntity.notFound().build();
    }
   }


}
