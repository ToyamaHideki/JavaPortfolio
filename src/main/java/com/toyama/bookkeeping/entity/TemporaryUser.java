package com.toyama.bookkeeping.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TemporaryUser {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String verificationCode;
    private LocalDateTime expiryAt;
    private LocalDateTime createdAt;
}