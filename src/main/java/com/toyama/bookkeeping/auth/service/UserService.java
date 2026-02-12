package com.toyama.bookkeeping.auth.service;

import com.toyama.bookkeeping.auth.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    /******************************************************************************************
     *  管理者向け機能
     ******************************************************************************************/
    List<User> findAll();



    /******************************************************************************************
     *  一般ユーザー向け機能
     ******************************************************************************************/

    User findByEmail(String email);

    //ユーザー情報を更新後、セッション内容を書き換える
    @Transactional
    void updateUserProfile(User user);
}
