package com.cats.apigeteway.conf;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Value("${role.admin}")
    private String admin;
    @Value("${role.hr}")
    private String hr;
    @Value("${role.user}")
    private String user;

    private final JwtAuthConverter jwtAuthConverter;
    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        LOG.info("Configuring Security Web Filter Chain");

        String[] AUTH_WHITELIST = {
                // -- swagger ui
                "/v2/api-docs",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/information-management-service/v3/api-docs",
                "/payroll-service/v3/api-docs",
                "/attendance-service/v3/api-docs",
                "/swagger-ui.html",
                "/webjars/**"
        };
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> {
                    LOG.info("Setting up route authorizations");
                    exchanges
                            .pathMatchers("/actuator/**").hasRole(admin.toUpperCase())
                            .pathMatchers(AUTH_WHITELIST).permitAll()
                            //.pathMatchers("/api/files/**").permitAll()
                            .pathMatchers("/eureka/**", "/actuator/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                            .anyExchange().permitAll();
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
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3005", "http://192.168.1.169:3005"));
        // configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3005","http://192.168.1.169:3005"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTION"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        LOG.info("CORS Source configuration completed");
        return source;
    }
}
