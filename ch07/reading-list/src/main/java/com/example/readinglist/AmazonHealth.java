package com.example.readinglist;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AmazonHealth implements HealthIndicator {

    @Override
    public Health health() {

        try {
            RestTemplate rest = new RestTemplate();
            rest.getForObject("http://www.amazon.com", String.class);
            int a = 1 / 0;
            return Health.up().build();
        } catch (Exception e) {
            return Health.down().withDetail("reason", e.getMessage()).build();
        }
    }

}
