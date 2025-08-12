package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.TokenRelayFilterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    @Order(1)
    RouterFunction<ServerResponse> routes() {
        return route()
                .before(BeforeFilterFunctions.uri("http://localhost:8080"))
                .before(BeforeFilterFunctions.rewritePath("/api", "/"))
                .filter(TokenRelayFilterFunctions.tokenRelay())
                .GET("/api/**", http())
                .build();
    }

    @Bean
    @Order(2)
    RouterFunction<ServerResponse> html() {
        return route()
                .before(BeforeFilterFunctions.uri("http://localhost:8020"))
                .filter(TokenRelayFilterFunctions.tokenRelay())
                .GET("/**", http())
                .build();
    }


}
