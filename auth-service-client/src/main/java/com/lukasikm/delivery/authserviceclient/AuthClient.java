package com.lukasikm.delivery.authserviceclient;

import com.lukasikm.delivery.authserviceclient.dto.CredentialsDTO;
import com.lukasikm.delivery.authserviceclient.dto.TokenDTO;
import com.lukasikm.delivery.authserviceclient.dto.UserDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("auth-service")
@RibbonClient("auth-service")
public interface AuthClient {
    @PostMapping("/api/auth/token")
    TokenDTO acquireToken(@RequestBody CredentialsDTO credentials);

    @GetMapping("/api/auth/user")
    UserDTO getCurrentUser(@RequestHeader("Authorization") String tokenHeader);
}
