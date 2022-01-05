package com.lukasikm.delivery.auth.api;

import com.lukasikm.delivery.auth.domain.TokenService;
import com.lukasikm.delivery.auth.domain.UserService;
import com.lukasikm.delivery.authserviceclient.AuthClient;
import com.lukasikm.delivery.authserviceclient.dto.CredentialsDTO;
import com.lukasikm.delivery.authserviceclient.dto.TokenDTO;
import com.lukasikm.delivery.authserviceclient.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthController implements AuthClient {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @Operation(summary = "Login or register and get token", tags = { "Token" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Credentials incorrect", content = @Content())
    })
    @Override
    public TokenDTO acquireToken(@Valid @RequestBody CredentialsDTO credentials) {
        var userDto = userService.loginOrRegister(credentials);

        return tokenService.createToken(userDto);
    }

    @Operation(summary = "Get current user info", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Token missing or invalid", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Token subject no longer exists", content = @Content())
    })
    @Override
    public UserDTO getCurrentUser(@RequestHeader("Authorization") String header) {
        var token = header.substring(7);
        var userId =  tokenService.verifyTokenValidAndGetId(token);

        return userService.getUserById(userId);
    }
}
