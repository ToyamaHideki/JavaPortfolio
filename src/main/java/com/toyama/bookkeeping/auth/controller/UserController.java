package com.toyama.bookkeeping.auth.controller;

import com.toyama.bookkeeping.auth.entity.User;
import com.toyama.bookkeeping.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/detail")
    public String getUserDetail(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        //ログイン情報からメールアドレス(一意)を取得
        String email = userDetails.getUsername();

        // DBからユーザー取得
        User user = userService.findByEmail(email);

        // 画面（HTML）にデータを渡す
        model.addAttribute("user", user);
        return "user/detail";
    }

    @GetMapping("/edit")
    public String showEditForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        // 現在ログインしているユーザーの情報を取得
        String email = userDetails.getUsername();

        //email(一意)から対象者のデータを抽出
        User user = userService.findByEmail(email);

        // 画面（HTML）にデータを渡す
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user) {
        // Serviceを呼び出す（この中でセッション更新まで完結する）
        userService.updateUserProfile(user);

        // 更新が終わったら詳細画面へ戻る
        return "redirect:/user/detail";
    }


}