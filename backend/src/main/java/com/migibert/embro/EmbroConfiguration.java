package com.migibert.embro;

import org.jooq.conf.RenderImplicitJoinType;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Validated
@EnableWebMvc
public class EmbroConfiguration implements WebMvcConfigurer {

    @Value("${app.cors.allowed.origins}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(allowedOrigins);
    }

    @Bean
    public DefaultConfigurationCustomizer configurationCustomiser() {
        return (DefaultConfiguration c) -> c.settings().withRenderImplicitJoinToManyType(RenderImplicitJoinType.INNER_JOIN);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        This is where we configure the security required for our endpoints and setup our app to serve as
        an OAuth2 Resource Server, using JWT validation.
        */
        return http
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .cors(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(allowedOrigins);
            }
        };
    }

}
