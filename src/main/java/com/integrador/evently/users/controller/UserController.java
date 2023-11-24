package com.integrador.evently.users.controller;


import com.integrador.evently.users.dto.Login;
import com.integrador.evently.users.dto.RegisterUser;
import com.integrador.evently.users.dto.UserDto;
import com.integrador.evently.users.model.UserType;
import com.integrador.evently.users.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/type/{userType}")
    public List<UserDto> getUserByType(@PathVariable String userType) {
        return userService.getUsersByType(userType);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody RegisterUser newUser) {
        return userService.registerUser(newUser);
    }

    @PostMapping("/login")
    public UserDto loginUser(@RequestBody Login credentials) {
        return userService.login(credentials);
    }
}
