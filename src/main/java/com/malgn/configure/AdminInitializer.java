package com.malgn.configure;

import com.malgn.user.domain.Role;
import com.malgn.user.domain.User;
import com.malgn.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initAdmin() {
        return args -> {

            String adminEmail = "admin@test.com";

            if(!userRepository.existsByEmail(adminEmail)) {

                User admin = User.builder()
                        .email(adminEmail)
                        .password(passwordEncoder.encode("admin1234"))
                        .name("admin")
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(admin);

                System.out.println("Admin 계정 생성 완료");
            }
        };
    }
}
