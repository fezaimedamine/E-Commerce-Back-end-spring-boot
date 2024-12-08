package com.SpringBootProject.proj1.service.adminCategory;

import java.util.List;

import org.springframework.stereotype.Service;

import com.SpringBootProject.proj1.Entitys.Category;
import com.SpringBootProject.proj1.Repositry.CategoryRepository;
import com.SpringBootProject.proj1.dto.CategoryDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService{
    private final CategoryRepository categoryRepository;
    public Category createCategory(CategoryDto categoryDto){
        Category category=new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

}



