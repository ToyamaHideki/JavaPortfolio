package com.toyama.bookkeeping.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    独自のログインフォームを実装する場合
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // ログイン・登録関連以外のすべてのリクエスト（/ を含む）を要認証にする
                        .requestMatchers("/login", "/user/signup", "/user/register", "/user/verify", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .usernameParameter("email") // ログイン時、HTML側が name="email" で判定するように指定
                        .permitAll()
                );

        return http.build();
    }


//                // デフォルトのログインフォームを有効化
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        // signupやverify、静的リソースは誰でもアクセス可能にする
//                        .requestMatchers("/user/signup", "/user/register", "/user/verify", "/css/**", "/js/**").permitAll()
//                        .anyRequest().authenticated() // それ以外はログインが必要
//                )
//                .formLogin(form -> form
//                        .usernameParameter("email") // これで入力欄の name="email" を探すようになる
//                        .defaultSuccessUrl("/home", true) // ログイン成功後の遷移先（まだなければ適当なパスでOK）
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/user/login?logout") // ログアウト後の遷移先
//                        .permitAll()
//                );
//
//        return http.build();
//    }
}