package com.example.readinglist.condition;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class MyService {

    @Bean
    @Conditional(JdbcTemplateCondition.class)
    public CommandLineRunner conditional() {
        return args -> System.out.println("MyService conditional");
    }

    @Bean
    @ConditionalOnClass(JdbcTemplate.class)
    public CommandLineRunner conditionalOnClass() {
        return args -> System.out.println("MyService conditionalOnClass");
    }

}
