package com.example.mamikos_api.config;

import com.example.mamikos_api.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Matikan CSRF untuk REST API
                .authorizeHttpRequests(auth -> auth
                        // 1. Endpoint Publik (Login & Register)
                        .requestMatchers("/api/auth/**").permitAll()

                        // 2. Endpoint Publik (Melihat Kosan)
                        .requestMatchers(HttpMethod.GET, "/api/kosan", "/api/kosan/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/kosan/{kosanId}/ulasan").permitAll()

                        // 3. Endpoint Terproteksi (ROLE_PEMILIK)
                        .requestMatchers(HttpMethod.POST, "/api/kosan").hasRole("PEMILIK")
                        .requestMatchers(HttpMethod.PUT, "/api/kosan/**").hasRole("PEMILIK")
                        .requestMatchers(HttpMethod.DELETE, "/api/kosan/**").hasRole("PEMILIK")
                        .requestMatchers(HttpMethod.PUT, "/api/orders/{id}/status").hasRole("PEMILIK")
                        .requestMatchers(HttpMethod.GET, "/api/orders/my-rentals").hasRole("PEMILIK")

                        // 4. Endpoint Terproteksi (ROLE_PENCARI)
                        .requestMatchers(HttpMethod.POST, "/api/orders").hasRole("PENCARI")
                        .requestMatchers(HttpMethod.GET, "/api/orders/my-bookings").hasRole("PENCARI")
                        .requestMatchers(HttpMethod.POST, "/api/kosan/{kosanId}/ulasan").hasRole("PENCARI")
                        .requestMatchers(HttpMethod.PUT, "/api/ulasan/**").hasRole("PENCARI")
                        .requestMatchers(HttpMethod.DELETE, "/api/ulasan/**").hasRole("PENCARI")

                        // 5. Endpoint lain harus diautentikasi
                        .anyRequest().authenticated()
                )
                // Konfigurasi session management menjadi STATELESS (wajib untuk JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider) // Set provider autentikasi
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Tambahkan filter JWT

        return http.build();
    }
}