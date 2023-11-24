package com.integrador.evently.categories.interfaces;

import com.integrador.evently.categories.dto.CategoryDTO;
import com.integrador.evently.categories.dto.NewCategoryDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryController {
    List<CategoryDTO> getAllCategories();
    ResponseEntity<CategoryDTO> getCategoryById(Long id);
    ResponseEntity<CategoryDTO> saveCategory(NewCategoryDTO categoryDTO);
//    ResponseEntity<CategoryDTO> updateCategory(Long id, CategoryDTO categoryDTO);
//    void deleteCategory(Long id);
}
