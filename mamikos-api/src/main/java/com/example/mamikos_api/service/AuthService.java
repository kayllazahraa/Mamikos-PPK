package com.example.mamikos_api.service;

import com.example.mamikos_api.dto.LoginRequest;
import com.example.mamikos_api.dto.LoginResponse;
import com.example.mamikos_api.dto.RegisterRequest;
import com.example.mamikos_api.dto.UpdateProfileRequest;
import com.example.mamikos_api.entity.Role;
import com.example.mamikos_api.entity.User;
import com.example.mamikos_api.jwt.JwtService;
import com.example.mamikos_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username sudah terdaftar");
        }

        Role userRole;
        try {
            userRole = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Role tidak valid. Gunakan 'ROLE_PENCARI' atau 'ROLE_PEMILIK'.");
        }

        User user = User.builder()
                .namaLengkap(request.getNamaLengkap())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nomorTelefon(request.getNomorTelefon())
                .role(userRole)
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .token(jwtToken)
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username tidak ditemukan"));

        String jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .token(jwtToken)
                .build();
    }

    public User updateProfile(User currentUser, UpdateProfileRequest request) {
        currentUser.setNamaLengkap(request.getNamaLengkap());

        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            if (!request.getUsername().equals(currentUser.getUsername())) {
                if (userRepository.existsByUsername(request.getUsername())) {
                    throw new IllegalArgumentException("Username sudah terdaftar, silakan gunakan username lain");
                }
                currentUser.setUsername(request.getUsername());
            }
        }

        if (request.getNomorTelefon() != null && !request.getNomorTelefon().isEmpty()) {
            currentUser.setNomorTelefon(request.getNomorTelefon());
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(currentUser);
    }
}