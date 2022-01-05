package com.lukasikm.delivery.auth.domain;

import com.lukasikm.delivery.auth.domain.exception.TokenException;
import com.lukasikm.delivery.authserviceclient.dto.TokenDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.lukasikm.delivery.authserviceclient.dto.UserDTO;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${auth.jwt-secret}")
    private String jwtSecret;

    public TokenDTO createToken(UserDTO userDTO) {
        var token = Jwts.builder()
                .setSubject(userDTO.getEmail())
                .setExpiration(Date.from(Instant.now().plusSeconds(60 * 60)))
                .setIssuer("delivery@lukasikm.com")
                .claim("role", userDTO.getRole())
                .claim("userId", userDTO.getId())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return new TokenDTO(token);
    }

    public UUID verifyTokenValidAndGetId(String token) {
        try {
            return UUID.fromString(Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("userId", String.class));
        } catch (ExpiredJwtException | MalformedJwtException ex) {
            logger.info("Invalid token detected, exception {}", ex.getMessage());

            throw new TokenException();
        }
    }
}
