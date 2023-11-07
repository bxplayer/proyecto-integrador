package com.integrador.evently.users.dto;

import com.integrador.evently.users.model.UserType;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private UserType userType;


}
