package com.example.readinglist;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class HttpTraceConfig {

//    @Bean
    public HttpTraceRepository httpTraceRepository() {
        InMemoryHttpTraceRepository inMemoryHttpTraceRepository = new InMemoryHttpTraceRepository();
        inMemoryHttpTraceRepository.setCapacity(100);
        return inMemoryHttpTraceRepository;
    }
}
