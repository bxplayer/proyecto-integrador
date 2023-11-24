package com.integrador.evently.products.dto;

import com.integrador.evently.categories.dto.CategoryDTO;
import com.integrador.evently.providers.model.Provider;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private List<String> imageUrls;
    private CategoryDTO category;
    private Provider provider;
}
