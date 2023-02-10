package com.example.readinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private ReaderRepository readerRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfig securityFilterChain");
        http
                .authorizeRequests()
                .anyRequest().hasRole("READER")

                .and()
                .formLogin()

                .and()

                .csrf().disable();
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        System.out.println("SecurityConfig userDetailsService");
        return username -> readerRepository.findById(username).orElseGet(this::defaultUser);
    }

    @Bean
    Reader defaultUser() {
        System.out.println("SecurityConfig defaultUser");
        Reader reader = new Reader();
        reader.setUsername("user");
        reader.setPassword(passwordEncoder().encode("1111"));
        reader.setFullname("test-user");
        return reader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("SecurityConfig passwordEncoder");
        return new BCryptPasswordEncoder();
    }
}
