package com.SpringBootProject.proj1.Controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootProject.proj1.Entitys.Category;
import com.SpringBootProject.proj1.dto.CategoryDto;
import com.SpringBootProject.proj1.service.adminCategory.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
//@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;
    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto){
        Category category=categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    @GetMapping("/admin/allcat")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    
}
