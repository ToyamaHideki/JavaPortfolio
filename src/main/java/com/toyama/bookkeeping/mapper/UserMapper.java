package com.toyama.bookkeeping.mapper;

import com.toyama.bookkeeping.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.Optional;

@Mapper
public interface UserMapper {
    // ユーザー名で検索するメソッド
    Optional<User> findByUsername(String username);

    // 新規ユーザーを登録するメソッド
    void insert(User user);
}