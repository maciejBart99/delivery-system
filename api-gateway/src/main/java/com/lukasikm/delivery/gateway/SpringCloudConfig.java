package com.lukasikm.delivery.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.lukasikm.delivery.authserviceclient.dto.UserRole.*;

@Configuration
public class SpringCloudConfig {
    private static final String AUTH_SERVICE = "lb://auth-service";
    private static final String ORDER_SERVICE = "lb://order-service";
    private static final String RETAIL_SERVICE = "lb://retail-service";
    private static final String WAREHOUSE_SERVICE = "lb://warehouse-service";

    @Autowired
    private AuthorizationFilterFactory authorization;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/auth/**")
                        .uri(AUTH_SERVICE))
                .route(p -> p
                        .method("GET").and().path("/api/orders")
                        .filters(f -> f.filter(authorization.forRoles(Driver, WarehouseManager)))
                        .uri(ORDER_SERVICE))
                .route(p -> p
                        .method("GET").and().path("/api/orders/*/**")
                        .filters(f -> f.filter(authorization.forRoles(Driver, WarehouseManager, Client)))
                        .uri(ORDER_SERVICE))
                .route(p -> p
                        .method("POST").and().path("/api/orders/*/**")
                        .filters(f -> f.filter(authorization.forRoles(Driver, WarehouseManager)))
                        .uri(ORDER_SERVICE))
                .route(p -> p
                        .path("/api/retail/**")
                        .filters(f -> f.filter(authorization.forRoles(Client)))
                        .uri(RETAIL_SERVICE))
                .route(p -> p
                        .path("/api/warehouse")
                        .filters(f -> f.filter(authorization.forRoles(WarehouseManager)))
                        .uri(WAREHOUSE_SERVICE))
                .route(p -> p
                        .path("/api/warehouse/*/**")
                        .filters(f -> f.filter(authorization.forRoles(Driver, WarehouseManager)))
                        .uri(WAREHOUSE_SERVICE))
                // swagger
                .route(p -> p
                        .path("/v3/api-docs/auth/**")
                        .filters(f -> f.rewritePath("/v3/api-docs/auth", "/v3/api-docs"))
                        .uri(AUTH_SERVICE))
                .route(p -> p
                        .path("/v3/api-docs/retail/**")
                        .filters(f -> f.rewritePath("/v3/api-docs/retail", "/v3/api-docs"))
                        .uri(RETAIL_SERVICE))
                .route(p -> p
                        .path("/v3/api-docs/order/**")
                        .filters(f -> f.rewritePath("/v3/api-docs/order", "/v3/api-docs"))
                        .uri(ORDER_SERVICE))
                .route(p -> p
                        .path("/v3/api-docs/warehouse/**")
                        .filters(f -> f.rewritePath("/v3/api-docs/warehouse", "/v3/api-docs"))
                        .uri(WAREHOUSE_SERVICE))
                .build();
    }

}