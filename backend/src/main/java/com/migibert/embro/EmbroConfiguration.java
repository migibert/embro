package com.migibert.embro;

import org.jooq.conf.RenderImplicitJoinType;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@Validated
public class EmbroConfiguration {
    @Bean
    public DefaultConfigurationCustomizer configurationCustomiser() {
        return (DefaultConfiguration c) -> c.settings().withRenderImplicitJoinToManyType(RenderImplicitJoinType.INNER_JOIN);
    }
}
