package com.integrador.evently.users.controller;

import com.integrador.evently.users.dto.NewUserDto;
import com.integrador.evently.users.model.User;
import com.integrador.evently.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService service;

//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        return ResponseEntity.ok().body((service.findById(id)));
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> createUser(@RequestBody NewUserDto user){
        String userId = service.createUser(user);
        return ResponseEntity.ok().body(Map.of("id", userId));
    }

    // TODO: 2021-10-13 @PreAuthorize("isAuthenticated(), hasRole('ADMIN')")
//    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
