package com.librarymanagement.LibraryApplication.configs;

import com.librarymanagement.LibraryApplication.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class Config {
    private final UserRepo userRepo;
    @Bean
    public UserDetailsService userDetailsService(){
        return userRepo::findUserByUsername;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public  PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


//    @Bean
//    public FilterRegistrationBean<ExceptionHandlingFilter> exceptionFilterRegistration(){
//        FilterRegistrationBean<ExceptionHandlingFilter> registrationBean =
//                new FilterRegistrationBean<>();
//        registrationBean.setFilter(new ExceptionHandlingFilter());
//        registrationBean.addUrlPatterns("/**");
//        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
//        return registrationBean;
//    }


}
