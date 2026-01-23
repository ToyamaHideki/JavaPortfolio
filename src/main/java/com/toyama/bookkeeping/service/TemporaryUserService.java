package com.toyama.bookkeeping.service;

import com.toyama.bookkeeping.entity.TemporaryUser;

public interface TemporaryUserService {
    /**
     * ユーザー入力情報を元に、認証コードを発行して仮登録を行います。
     */
    TemporaryUser createTemporaryUser(String username, String email, String password);

    /**
     * 入力されたコードを検証します。
     */
    boolean verifyCode(String email, String code);
}