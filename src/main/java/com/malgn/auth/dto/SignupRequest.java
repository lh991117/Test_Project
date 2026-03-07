package com.malgn.auth.dto;

public record SignupRequest(
        String email,
        String password,
        String name
) {
}
