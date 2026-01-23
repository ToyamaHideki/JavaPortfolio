package com.toyama.bookkeeping.mapper;

import com.toyama.bookkeeping.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface UserMapper {

    /**
     * jdbcType=OTHER を指定すると、MyBatisは型推測を放棄する。
     * 「中身をそのままPostgreSQLのドライバに丸投げ」で依頼可能とする。
     * PostgreSQLのドライバが「JavaのUUIDオブジェクトだから、DBのUUID型として扱えばいいんだな」と正しく解釈
     * */
    @Select("SELECT * FROM users WHERE id = #{id, jdbcType=OTHER}") // jdbcType=OTHER を明示
    Optional<User> findById(@Param("id") UUID id);


    @Insert("INSERT INTO users (username, email, password, role) VALUES (#{username}, #{email}, #{password}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(User user);

    /**
     * 全ユーザーを取得する例（必要に応じて）
     */
    @Select("SELECT * FROM users")
    List<User> findAll();

    //ログインユーザー(email)を検索する。
    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<User> findByEmail(String email);
}