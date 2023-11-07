package com.integrador.evently.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String id;

    private String username;

    private String email;

    private String firstName;

    private String surname;

    private UserType type;
}
