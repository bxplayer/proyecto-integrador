package com.integrador.evently.users.repository;


import com.integrador.evently.users.dto.NewUserDto;
import com.integrador.evently.users.model.User;

public interface UserRepository {

  User findById(Long id);

  String addUser(NewUserDto user);

  void delete(String id);
}