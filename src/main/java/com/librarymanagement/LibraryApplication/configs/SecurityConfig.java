package com.librarymanagement.LibraryApplication.configs;

import com.librarymanagement.LibraryApplication.enums.Role;
import com.librarymanagement.LibraryApplication.exceptions.JwtAuthenticationEntryPont;
import com.librarymanagement.LibraryApplication.jwtconfigs.JwtAuthenticationFilter;
import com.librarymanagement.LibraryApplication.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationEntryPont jwtAuthenticationEntryPont;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(Constants.PUBLIC_ACCESS_URI)
                                .permitAll()
                                .requestMatchers(Constants.USER_ACCESS_URI).hasAnyRole(Role.USER.name(),
                                Role.LIBRARIAN.name())
                                .requestMatchers("/api/v1/**").hasRole(Role.LIBRARIAN.name())
                                .anyRequest()
                                .authenticated()

                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPont))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
