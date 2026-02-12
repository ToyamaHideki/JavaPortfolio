package com.toyama.bookkeeping.auth.mapper;

import com.toyama.bookkeeping.auth.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface UserMapper {


    //全ユーザーを取得する例（ADMIN機能限定）
    @Select("SELECT * FROM users")
    List<User> findAll();

    //編集対象のIDを検索する。
    @Select("SELECT * FROM users WHERE id = #{id}")
    Optional<User> findById(UUID id);

    //ログインユーザー(email)を検索する。
    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<User> findByEmail(String email);


    //新規ユーザー登録(仮ユーザー　→　ユーザー登録時)
    @Insert("INSERT INTO users (username, email, password, role) VALUES (#{username}, #{email}, #{password}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(User user);

    //既存ユーザーの登録変更（ユーザー操作）
    @Update("UPDATE users SET username = #{username}, email = #{email}, password = #{password} " +
            "WHERE id = #{id, typeHandler=org.apache.ibatis.type.ObjectTypeHandler}")
    void update(User user);
}