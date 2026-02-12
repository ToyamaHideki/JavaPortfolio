package com.toyama.bookkeeping.auth.service.imple;

import com.toyama.bookkeeping.auth.entity.User;
import com.toyama.bookkeeping.auth.mapper.UserMapper;
import com.toyama.bookkeeping.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    /******************************************************************************************
     *  管理者向け機能
     ******************************************************************************************/
    @Override
    public List<User> findAll() {
        var users = userMapper.findAll();
        // ここで加工ロジックなどを入れる場合も、具体的な型名を繰り返さず疎に保てる
        return users;
    }


    /******************************************************************************************
     *  一般ユーザー向け機能
     ******************************************************************************************/


    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("指定されたユーザー(ID: " + email + ")は見つかりませんでした"));
    }


    //ユーザー情報を更新後、セッション内容を書き換える
    @Transactional
    @Override
    public void updateUserProfile(User user) {

        //TODO:更新処理の実装。
        //1.IDの存在チェック
        //↑既にログイン状態のユーザーに対する操作の為不要。
        if(userMapper.findById(user.getId()).isEmpty()){
            throw new RuntimeException("指定されたユーザー(ID: " + user.getId() + ")は見つかりませんでした");
        };

        // 1. まずDBを更新する（作成したMapperを呼び出す）
        userMapper.update(user);

        // 2. 現在のセッション（ログイン情報）を上書きする
        // これをしないと、メールアドレスを変更した瞬間に「今のログイン者」と「DB」にズレが生じます
        UserDetails newUserDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword()) // DBにあるハッシュ化済みのものをそのまま使う
                .roles(user.getRole())        // 既存の権限
                .build();

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                newUserDetails,
                null, // 認証済みなのでパスワードはnullでも大丈夫です
                newUserDetails.getAuthorities()
        );

        // 現在のコンテキスト（門番の持ち物）に新しい情報をセットする
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}