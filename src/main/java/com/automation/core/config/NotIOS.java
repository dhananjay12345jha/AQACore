package com.automation.core.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class NotIOS implements Condition {
    @Override
    public boolean matches(final ConditionContext context,
                           final AnnotatedTypeMetadata metadata) {
        final Environment env = context.getEnvironment();
        return !(env.getProperty("core_ENV#platformName", "").toLowerCase().startsWith("ios") ||
                env.getProperty("core_ENV#platformName", "").toLowerCase().startsWith("android"));
    }
}