package com.example.readinglist.condition;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(JdbcTemplateCondition.class)
public class MyService {

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> System.out.println("MyService");
    }

}
