package com.toyama.bookkeeping.controller;

import com.toyama.bookkeeping.service.TemporaryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor // これで TemporaryUserService が自動注入されます
public class RegistrationController {

    private final TemporaryUserService temporaryUserService;

    /**
     * 1. 新規登録画面を表示する
     * GET /user/signup
     */
    @GetMapping("/signup")
    public String showSignupForm() {
        return "user/signup"; // templates/user/signup.html を表示
    }

    /**
     * 2. 登録フォームの送信を受け付け、仮登録を実行する
     * POST /user/register
     */
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password) {

        // Serviceを呼び出して temporary_users テーブルに保存
        temporaryUserService.createTemporaryUser(username, email, password);

        // 認証画面へ移動。その際、誰の認証か判別するために email をパラメータに渡す
        return "redirect:/user/verify?email=" + email;
    }


    // 3. 認証コード入力画面の表示 (ここがGETで呼ばれます)
    @GetMapping("/verify")
    public String showVerifyForm(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        // 成功メッセージなどを表示したい場合はここに追加可能
        return "user/verify"; // templates/user/verify.html を探す
    }

    /**
     * 3. 認証コード入力画面を表示する
     * GET /user/verify
     */
    @PostMapping("/verify")
    public String verify(@RequestParam String email, @RequestParam String code) {
        // verifyCodeメソッドの中で、本登録(usersへの移送)まで完結するようにしました
        boolean success = temporaryUserService.verifyCode(email, code);

        if (success) {
            // 成功したらログイン画面（まだ作っていなければ仮の完了画面）へ
            return "redirect:/user/login?registered";
        } else {
            // 失敗（コード間違い・期限切れ）ならエラーを表示して戻す
            return "redirect:/user/verify?email=" + email + "&error";
        }
    }
}