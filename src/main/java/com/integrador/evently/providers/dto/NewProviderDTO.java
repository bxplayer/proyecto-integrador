package com.integrador.evently.providers.dto;

import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.users.dto.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class NewProviderDTO {
    private String name;
    private String information;
    private String address;
    private List<Long> productIds;
    private UserDto user;
}
