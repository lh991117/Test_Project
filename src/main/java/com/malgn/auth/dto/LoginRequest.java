package com.malgn.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}
