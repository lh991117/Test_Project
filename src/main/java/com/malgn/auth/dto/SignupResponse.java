package com.malgn.auth.dto;

public record SignupResponse(
        Long id,
        String email,
        String name,
        String role
) {
}
