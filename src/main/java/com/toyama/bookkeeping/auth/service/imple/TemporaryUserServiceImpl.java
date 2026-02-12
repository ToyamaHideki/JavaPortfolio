package com.toyama.bookkeeping.auth.service.imple;

import com.toyama.bookkeeping.auth.entity.TemporaryUser;
import com.toyama.bookkeeping.auth.entity.User;
import com.toyama.bookkeeping.auth.mapper.TemporaryUserMapper;
import com.toyama.bookkeeping.auth.mapper.UserMapper;
import com.toyama.bookkeeping.auth.service.TemporaryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class TemporaryUserServiceImpl implements TemporaryUserService {

    private final TemporaryUserMapper temporaryUserMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TemporaryUser createTemporaryUser(String username, String email, String password) {
        TemporaryUser tempUser = new TemporaryUser();
        tempUser.setUsername(username);
        tempUser.setEmail(email);
        String encodedPassword = passwordEncoder.encode(password);
        tempUser.setPassword(encodedPassword);

        // 6桁の認証コード生成
        String code = String.format("%06d", new SecureRandom().nextInt(1000000));
        tempUser.setVerificationCode(code);

        // 有効期限の設定 (30分)
        tempUser.setExpiryAt(LocalDateTime.now().plusMinutes(30));

        // 保存
        temporaryUserMapper.insert(tempUser);

        return tempUser;
    }

    @Override
    @Transactional
    public boolean verifyCode(String email, String code) {
        // 1. コードが一致し、期限内のデータを取得
        return temporaryUserMapper.findValidCode(email, code).map(tempUser -> {
            // 2. 本登録用エンティティに詰め替え
            User user = new User();
            user.setUsername(tempUser.getUsername());
            user.setEmail(tempUser.getEmail());
            user.setPassword(tempUser.getPassword());
            user.setRole("USER");


            // 3. usersテーブルへ保存
            userMapper.insert(user);

            // 4. 不要になった仮登録データを削除
            temporaryUserMapper.deleteByEmail(email);

            return true;


        // 一致しなければfalse
        }).orElse(false);
    }
}