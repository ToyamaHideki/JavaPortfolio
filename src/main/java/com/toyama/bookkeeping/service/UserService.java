package com.toyama.bookkeeping.service;

import com.toyama.bookkeeping.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);
}
