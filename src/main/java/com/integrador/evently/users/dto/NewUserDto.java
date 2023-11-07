package com.integrador.evently.users.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.integrador.evently.users.model.UserType;
import com.integrador.evently.users.repository.PasswordHashing;

public class NewUserDto {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private UserType userType;

    @JsonCreator
    public NewUserDto(String userName, String password, String email, String firstName, String lastName, UserType userType) {
        this.userName = userName;
        try {
            this.password = PasswordHashing.generateHashedPassword(password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }
}
