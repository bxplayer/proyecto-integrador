package com.digitalmedia.users.service;

import com.digitalmedia.users.dto.NewUserDto;
import com.digitalmedia.users.dto.UserLoginDto;
import com.digitalmedia.users.model.User;
import com.digitalmedia.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(String id){
        return userRepository.findById(id);
    }

    public String createUser(NewUserDto user) {
        return userRepository.addUser(user);
    }

    public void delete(String id) {
        userRepository.delete(id);
    }
}
