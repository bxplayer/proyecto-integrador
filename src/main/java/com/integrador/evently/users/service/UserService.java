package com.integrador.evently.users.service;

import com.integrador.evently.users.model.User;
import com.integrador.evently.users.dto.UserDto;
import com.integrador.evently.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public UserDto createUser(UserDto userDto) {
        userRepository.findByUserName(userDto.getUserName())
                .ifPresent(user -> {
                    throw new RuntimeException("User already exists");
                });
        User user = modelMapper.map(userDto, User.class);
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}
