package com.SpringBootProject.proj1.service.adminCategory;

import java.util.List;

import com.SpringBootProject.proj1.Entitys.Category;
import com.SpringBootProject.proj1.dto.CategoryDto;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);
    List<Category> getAllCategories();
}
