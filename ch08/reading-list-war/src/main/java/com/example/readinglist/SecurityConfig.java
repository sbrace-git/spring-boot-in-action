package com.example.readinglist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Profile("!dev")
@EnableWebSecurity
public class SecurityConfig {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private ReaderRepository readerRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("SecurityConfig securityFilterChain");
        http
                .authorizeRequests()
                .requestMatchers(EndpointRequest.to("shutdown")).hasRole("ADMIN")
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().hasRole("READER")

                .and()
                .formLogin()

                .and()

                .csrf().disable();
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        logger.info("SecurityConfig userDetailsService");
        return username -> {
            logger.info("username = {}", username);
            if ("admin".equals(username)) {
                return adminUser();
            }
            return readerRepository.findById(username).orElseGet(this::defaultUser);
        };
    }

    @Bean
    Reader adminUser() {
        logger.info("SecurityConfig defaultUser");
        Reader reader = new Reader();
        reader.setUsername("admin");
        reader.setPassword(passwordEncoder().encode("1111"));
        reader.setFullname("admin-user");
        return reader;
    }

    @Bean
    Reader defaultUser() {
        logger.info("SecurityConfig defaultUser");
        Reader reader = new Reader();
        reader.setUsername("user");
        reader.setPassword(passwordEncoder().encode("1111"));
        reader.setFullname("test-user");
        return reader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("SecurityConfig passwordEncoder");
        return new BCryptPasswordEncoder();
    }
}
