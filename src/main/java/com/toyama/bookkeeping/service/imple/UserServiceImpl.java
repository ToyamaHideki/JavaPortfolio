package com.toyama.bookkeeping.service.imple;

import com.toyama.bookkeeping.entity.User;
import com.toyama.bookkeeping.mapper.UserMapper;
import com.toyama.bookkeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Mapperへの依存も不変（final）にする
    private final UserMapper userMapper;

    @Override
    public List<User> findAll() {
        var users = userMapper.findAll();
        // ここで加工ロジックなどを入れる場合も、具体的な型名を繰り返さず疎に保てる
        return users;
    }

    @Override
    public User findById(Long id) {
        // findById() が Optional<User> を返すので、.orElseThrow() で処理
        return userMapper.findById(id)
                .orElseThrow(() -> new RuntimeException("指定されたユーザー(ID: " + id + ")は見つかりませんでした"));
    }
}