package com.digitalmedia.users.repository;

import com.digitalmedia.users.dto.NewUserDto;
import com.digitalmedia.users.model.User;


public interface UserRepository {

  User findById(String id);

  String addUser(NewUserDto user);

  void delete(String id);
}