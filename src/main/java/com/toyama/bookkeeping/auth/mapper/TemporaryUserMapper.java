package com.toyama.bookkeeping.auth.mapper;

import com.toyama.bookkeeping.auth.entity.TemporaryUser;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface TemporaryUserMapper {

    /******************************************************************************************************************
     * 仮登録情報をデータベースに保存。
     * ****************************************************************************************************************
     * *    @Insert: 実行するSQLを記述。
     * ****************************************************************************************************************
     * *    @Options:
     *      - useGeneratedKeys = true: データベース側で自動生成されたキー（UUID）を取得する設定。
     *      - keyProperty = "id": 生成された値を TemporaryUser オブジェクトの "id" フィールドにセットする。
     *      ※ id カラムは schema.sql で DEFAULT gen_random_uuid() を指定しているため、INSERT文の項目からは除外(DB側で自動生成)。
     ******************************************************************************************************************/
    @Insert("INSERT INTO temporary_users (username, password, email, verification_code, expiry_at) " +
            "VALUES (#{username}, #{password}, #{email}, #{verificationCode}, #{expiryAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TemporaryUser temporaryUser);

    /******************************************************************************************************************
     * メールアドレスと認証コードが一致し、かつ期限内のデータを1件取得します。
     * ****************************************************************************************************************
     * *    @Param: 実行するSQLを記述。
     ******************************************************************************************************************/
    @Select("SELECT * FROM temporary_users " +
            "WHERE email = #{email} AND verification_code = #{code} " +
            "AND expiry_at > CURRENT_TIMESTAMP")
    Optional<TemporaryUser> findValidCode(@Param("email") String email, @Param("code") String code);

    // 認証成功時にそのアドレスのデータを消す
    @Delete("DELETE FROM temporary_users WHERE email = #{email}")
    void deleteByEmail(@Param("email") String email);

    // 現在時刻より前の有効期限データをすべて削除する
    @Delete("DELETE FROM temporary_users WHERE expiry_at < CURRENT_TIMESTAMP")
    void deleteExpired();
}