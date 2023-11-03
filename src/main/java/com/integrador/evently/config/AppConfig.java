package com.integrador.evently.config;

import com.integrador.evently.activities.dto.ActivityDTO;
import com.integrador.evently.activities.model.Activity;
import com.integrador.evently.categories.dto.CategoryDTO;
import com.integrador.evently.categories.model.Category;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        //TypeMap<Category, CategoryDTO> categoryToCategoryDTOTypeMap = modelMapper.createTypeMap(Category.class, CategoryDTO.class);
        //TypeMap<Activity, ActivityDTO> activityToActivityDTOTypeMap = modelMapper.createTypeMap(Activity.class, ActivityDTO.class);

        return modelMapper;
    }
}