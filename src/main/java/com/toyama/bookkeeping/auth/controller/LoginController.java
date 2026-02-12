package com.toyama.bookkeeping.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    // localhost:8080/ にアクセスした時の処理
    @GetMapping("/")
    public String index() {
        // ここで /home にリダイレクトさせる
        // 未ログインなら SecurityConfig の門番が /login へ飛ばし、
        // ログイン済みならそのまま /home へ行ける、という仕組みになります
        return "redirect:/home";
    }

}
