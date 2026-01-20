package com.toyama.bookkeeping.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data // Getter, Setter, toStringなどを自動生成（Lombok）
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}