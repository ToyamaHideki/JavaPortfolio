package com.toyama.bookkeeping.service;

import com.toyama.bookkeeping.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    User findById(UUID id);
}
