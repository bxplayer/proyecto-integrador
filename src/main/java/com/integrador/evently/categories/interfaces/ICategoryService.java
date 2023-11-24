package com.integrador.evently.categories.interfaces;

import com.integrador.evently.categories.dto.CategoryDTO;
import com.integrador.evently.categories.dto.NewCategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    CategoryDTO saveCategory(NewCategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}
