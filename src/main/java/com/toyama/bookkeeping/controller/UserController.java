package com.toyama.bookkeeping.controller;

import com.toyama.bookkeeping.entity.User;
import com.toyama.bookkeeping.mapper.UserMapper; // 以前作成したもの
import com.toyama.bookkeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/detail/{id}")
    public String getUserDetail(@PathVariable("id") Long id, Model model) {
        // DBからユーザー取得（UserMapperにfindByIdメソッドがある前提）
        var user = userService.findById(id);

        // 画面（HTML）にデータを渡す
        model.addAttribute("user", user);

        // 表示するHTMLファイル名（src/main/resources/templates/user/detail.html）
        return "user/detail";
    }
}