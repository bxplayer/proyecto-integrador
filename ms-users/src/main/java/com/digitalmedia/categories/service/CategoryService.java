package com.digitalmedia.categories.service;

import com.digitalmedia.categories.dto.CategoryDTO;
import com.digitalmedia.categories.interfaces.ICategoryService;
import com.digitalmedia.categories.model.Category;
import com.digitalmedia.categories.repository.CategoryRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories =  categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return (category != null) ? modelMapper.map(category, CategoryDTO.class) : null;
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);

        if (existingCategory != null) {
            existingCategory.setName(categoryDTO.getName());

            Category updatedCategory = categoryRepository.save(existingCategory);
            return modelMapper.map(updatedCategory, CategoryDTO.class);
        }

        return null;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
