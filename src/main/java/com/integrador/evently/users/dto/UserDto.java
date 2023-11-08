package com.integrador.evently.users.dto;

import com.integrador.evently.users.model.UserType;

public record UserDto(Long id, String userName, String email, String firstName, String lastName, UserType type) {
}
