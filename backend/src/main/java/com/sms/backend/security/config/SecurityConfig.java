package com.sms.backend.security.config;

import com.sms.backend.security.filter.JwtAuthenticationFilter;
import com.sms.backend.security.handler.RestAccessDeniedHandler;
import com.sms.backend.security.handler.RestAuthenticationEntryPoint;
import com.sms.backend.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final RestAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/register").hasRole("ADMIN")
                        .requestMatchers("/api/v1/health").permitAll()

                        .requestMatchers(
                                "/api/v1/courses/me",
                                "/api/v1/faculty/me"
                        ).hasAnyRole("ADMIN", "FACULTY")

                        .requestMatchers(
                                "/api/v1/departments/**",
                                "/api/v1/faculty/**",
                                "/api/v1/courses/**",
                                "/api/v1/users/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                "/api/v1/dashboard",
                                "/api/v1/dashboard/**",
                                "/api/v1/students/me",
                                "/api/v1/student-courses/me",
                                "/api/v1/attendance/me",
                                "/api/v1/marks/me"
                        ).hasAnyRole("ADMIN", "FACULTY", "STUDENT")

                        .requestMatchers(
                                "/api/v1/students/**",
                                "/api/v1/student-courses/**",
                                "/api/v1/attendance/**",
                                "/api/v1/marks/**"
                        ).hasAnyRole("ADMIN", "FACULTY")

                        .requestMatchers("/api/v1/announcements/**")
                        .authenticated()

                        .anyRequest().authenticated()
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                .authenticationProvider(authenticationProvider())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}