package com.cats.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    private final RouteLocatorBuilder builder;
    @Bean
    public RouteLocator routeLocator(){
        return builder
                .routes()
                .route(r -> r.path("/attendance-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://attendance-service"))
                .route(r -> r.path("/information-management-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://information-management-service"))
                .route(r -> r.path(" /payroll-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://payroll-service"))
                .build();

    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gateway")
                        .version("1.0")
                        .description("API Gateway for Microservices"));
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");  // Allow all origins
        config.addAllowedHeader("*");  // Allow all headers
        config.addAllowedMethod("*");  // Allow all methods
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }



}