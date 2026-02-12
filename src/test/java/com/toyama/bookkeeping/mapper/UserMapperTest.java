package com.toyama.bookkeeping.mapper;

import com.toyama.bookkeeping.auth.mapper.UserMapper;
import com.toyama.bookkeeping.auth.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
// 実際のDocker（PostgreSQL）に接続してテストするために必要
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("ユーザーを保存し、ユーザー名で検索できること")
    void testInsertAndFindByUsername() {
        // 1. テストデータの準備
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("rawpassword"); // 本来はハッシュ化しますが、今はテスト用
        user.setRole("USER");

        // 2. 実行（保存）
        userMapper.insert(user);

        // 3. 検証（検索して一致するか）
        Optional<User> foundUser = userMapper.findByUsername("testuser");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
        assertThat(foundUser.get().getId()).isNotNull(); // IDが自動採番されているか
    }
}