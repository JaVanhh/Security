package com.demo.secutity.service.impl;

import com.demo.secutity.config.JwtService;
import com.demo.secutity.dto.LoginRequest;
import com.demo.secutity.dto.LoginResponse;
import com.demo.secutity.dto.RegisterRequest;
import com.demo.secutity.entity.Role;
import com.demo.secutity.entity.User;
import com.demo.secutity.repository.RoleRepository;
import com.demo.secutity.repository.UserRepository;
import com.demo.secutity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public LoginResponse login(LoginRequest request) {
        // Xác thực thông tin đăng nhập
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

        // Tìm user từ cơ sở dữ liệu
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow();

        // Tạo token JWT cho user
        var jwtToken = jwtService.generateToken(user);

        // Trả về token trong response
        return LoginResponse.builder().accessToken(jwtToken).build();

    }

    public LoginResponse registerUser(RegisterRequest request) {
        var user = User.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Error: Role USER is not found."));
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder().accessToken(jwtToken).build();
    }
}
