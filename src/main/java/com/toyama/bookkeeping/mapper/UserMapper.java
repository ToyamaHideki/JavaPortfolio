package com.toyama.bookkeeping.mapper;

import com.toyama.bookkeeping.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    /**
     * IDを指定してユーザーを1件取得する
     * 副問い合わせなどの複雑なSQLが必要になっても、この"""内に記述可能です
     */
    @Select("SELECT * FROM users WHERE id = #{id}")
    Optional<User> findById(Long id);

    /**
     * 全ユーザーを取得する例（必要に応じて）
     */
    @Select("SELECT * FROM users")
    List<User> findAll();
}