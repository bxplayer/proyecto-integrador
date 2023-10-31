package com.digitalmedia.products.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    //private Set<Long> associationIds;
}
