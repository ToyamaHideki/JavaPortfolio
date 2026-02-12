package com.toyama.bookkeeping.auth.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class User {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String role;
    private boolean isActive;          // 有効フラグ
    private boolean isPartnerLinked;   // 連携フラグ
    private LocalDateTime createdAt;
}