package com.integrador.evently.users.service;

import com.integrador.evently.users.dto.NewUserDto;
import com.integrador.evently.users.model.User;
import com.integrador.evently.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id);
    }

    public String createUser(NewUserDto user) {
        return userRepository.addUser(user);
    }

    public void delete(String id) {
        userRepository.delete(id);
    }
}
