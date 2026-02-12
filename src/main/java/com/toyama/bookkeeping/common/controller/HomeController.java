package com.toyama.bookkeeping.common.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/dashboard")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // userDetails.getUsername() で、CustomUserDetailsServiceで設定したemailが取れます
        model.addAttribute("email", userDetails.getUsername());
        return "common/dashboard";
    }
}