package com.integrador.evently.users.service;

import com.integrador.evently.users.dto.Login;
import com.integrador.evently.users.dto.RegisterUser;
import com.integrador.evently.users.dto.UserDto;
import com.integrador.evently.users.model.User;
import com.integrador.evently.users.model.UserType;
import com.integrador.evently.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserDto> getUsersByType(String userType) {
        String upperUserType = userType.toUpperCase();
        if(!isValidUserType(upperUserType)) return Collections.emptyList();
        return userRepository.findByType(UserType.valueOf(upperUserType)).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    private boolean isValidUserType(String userTypeString) {
        for (UserType userType : UserType.values()) {
            if (userType.name().equalsIgnoreCase(userTypeString)) {
                return true;
            }
        }
        return false;
    }

    public UserDto registerUser(RegisterUser newUser) {
        User user = modelMapper.map(newUser, User.class);
        user.setType(UserType.USER);
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto login(Login credentials) {
        User user = userRepository.findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword())
                .orElse(null);
        return user != null ? modelMapper.map(user, UserDto.class) : null;
    }
}
