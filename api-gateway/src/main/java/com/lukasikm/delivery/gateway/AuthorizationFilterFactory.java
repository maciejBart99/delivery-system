package com.lukasikm.delivery.gateway;

import com.lukasikm.delivery.authserviceclient.AuthClient;
import com.lukasikm.delivery.authserviceclient.dto.UserRole;
import feign.FeignException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthorizationFilterFactory extends AbstractGatewayFilterFactory<AuthorizationFilterFactory.Config> {

    @Autowired
    private AuthClient authClient;

    public AuthorizationFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            try {
                var payload = authClient.getCurrentUser(tokenHeader);

                if (!config.getPermittedRoles().contains(payload.getRole())) {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }

                var enrichedRequest = exchange.getRequest()
                        .mutate()
                        .header("x-auth-id", payload.getId())
                        .build();
                return chain.filter(exchange.mutate().request(enrichedRequest).build());
            } catch (FeignException ex) {
                if (ex.status() == 401) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                } else {
                    throw ex;
                }
            }
        };
    }

    public GatewayFilter forRoles(UserRole ...permittedRoles) {
        return apply(new Config(Arrays.asList(permittedRoles)));
    }

    @Data
    public static class Config {
        private final List<UserRole> permittedRoles;
    }
}