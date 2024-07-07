package com.cats.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@ComponentScan
@EnableWebFluxSecurity
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthConverter jwtAuthConverter;
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.cors(cors -> cors.configurationSource(
                corsConfigurationSource("10.10.10.1")
        ));
        http.csrf(AbstractHttpConfigurer::disable);
        http.securityMatcher("/**").authorizeHttpRequests(auth -> {
            auth.requestMatchers("/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/webjars/**").permitAll();
            auth.anyRequest().authenticated();
        });

        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(
                                jwtAuthConverter
                        )
                )
        );

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }



    private UrlBasedCorsConfigurationSource corsConfigurationSource(String... origins) {
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(origins));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
