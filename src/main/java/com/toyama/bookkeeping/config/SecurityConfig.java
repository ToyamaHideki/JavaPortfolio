package com.toyama.bookkeeping.config;

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
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/user/signup", "/user/register", "/user/verify", "/css/**", "/js/**").permitAll() // 登録関連は誰でもOK
//                        .anyRequest().authenticated() // それ以外はログインが必要
//                )
//                .formLogin(login -> login
//                        .loginPage("/user/login") // 自作のログインページを使う宣言
//                        .permitAll()
//                )
//                .logout(logout -> logout.permitAll());
//
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // signupやverify、静的リソースは誰でもアクセス可能にする
                        .requestMatchers("/user/signup", "/user/register", "/user/verify", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated() // それ以外はログインが必要
                )
                // デフォルトのログインフォームを有効化
                .formLogin(form -> form
                        .usernameParameter("email") // これで入力欄の name="email" を探すようになる
                        .defaultSuccessUrl("/home", true) // ログイン成功後の遷移先（まだなければ適当なパスでOK）
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/user/login?logout") // ログアウト後の遷移先
                        .permitAll()
                );

        return http.build();
    }
}