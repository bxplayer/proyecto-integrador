package com.digitalmedia.categories.interfaces;

import com.digitalmedia.categories.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryController {
    List<CategoryDTO> getAllCategories();
    ResponseEntity<CategoryDTO> getCategoryById(Long id);
    ResponseEntity<CategoryDTO> saveCategory(CategoryDTO categoryDTO);
    ResponseEntity<CategoryDTO> updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}
