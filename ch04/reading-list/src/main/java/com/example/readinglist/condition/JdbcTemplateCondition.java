package com.example.readinglist.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

public class JdbcTemplateCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        try {
            Objects.requireNonNull(context.getClassLoader())
                    .loadClass("org.springframework.jdbc.core.JdbcTemplate");
//                    .loadClass("org.springframework.jdbc.core.JdbcTemplate111");
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
