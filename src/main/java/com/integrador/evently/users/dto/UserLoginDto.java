package com.integrador.evently.users.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class UserLoginDto {
    private final String username;
    private final String password;

    @JsonCreator
    UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
