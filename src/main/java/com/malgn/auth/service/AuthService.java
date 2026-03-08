package com.malgn.auth.service;

import com.malgn.auth.dto.LoginRequest;
import com.malgn.auth.dto.LoginResponse;
import com.malgn.auth.dto.SignupRequest;
import com.malgn.auth.dto.SignupResponse;
import com.malgn.configure.security.JwtTokenProvider;
import com.malgn.user.domain.Role;
import com.malgn.user.domain.User;
import com.malgn.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {
        if(userRepository.existsByEmail(signupRequest.email())){
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = User.builder()
                .email(signupRequest.email())
                .password(passwordEncoder.encode(signupRequest.password()))
                .name(signupRequest.name())
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        return new SignupResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getRole().toString()
        );
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if(!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), String.valueOf(user.getRole()));
        return new LoginResponse(accessToken);
    }
}
