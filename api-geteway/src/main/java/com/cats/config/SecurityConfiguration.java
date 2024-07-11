package com.cats.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;


@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
@ComponentScan
public class SecurityConfiguration {

   private final JwtAuthConverter jwtAuthConverter;
   private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        LOG.info("Configuring Security Web Filter Chain");

        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> {
                    LOG.info("Setting up route authorizations");
                    exchanges
                            .pathMatchers("/actuator/**").permitAll()
                            .pathMatchers("/eureka/**").permitAll()
                            .pathMatchers("/openapi/**").permitAll()
                            .pathMatchers("/webjars/**").permitAll()
                            .anyExchange().authenticated();
                })
                .cors(cors -> {
                    LOG.info("Configuring CORS");
                    cors.configurationSource(corsConfigurationSource());
                });

        http.oauth2ResourceServer(oauth2 -> {
            LOG.info("Configuring OAuth2 Resource Server with JWT");
            oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter));
        });

        LOG.info("Security Web Filter Chain configuration completed");
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        LOG.info("Configuring CORS Source");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Modify as needed
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        LOG.info("CORS Source configuration completed");
        return source;
    }

}
