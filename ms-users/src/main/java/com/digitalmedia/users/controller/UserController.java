package com.digitalmedia.users.controller;

import com.digitalmedia.users.dto.NewUserDto;
import com.digitalmedia.users.model.User;
import com.digitalmedia.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService service;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable String id){
        return ResponseEntity.ok().body((service.findById(id)));
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> createUser(@RequestBody NewUserDto user){
        String userId = service.createUser(user);
        return ResponseEntity.ok().body(Map.of("id", userId));
    }

    // TODO: 2021-10-13 @PreAuthorize("isAuthenticated(), hasRole('ADMIN')")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
