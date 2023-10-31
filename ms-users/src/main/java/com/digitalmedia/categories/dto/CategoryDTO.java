package com.digitalmedia.categories.dto;

import com.digitalmedia.activities.dto.ActivityDTO;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private List<ActivityDTO> services;
}