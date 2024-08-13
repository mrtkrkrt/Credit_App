package com.mrtkrkrt.creditapp.auth.service.token.jwt;

import com.mrtkrkrt.creditapp.auth.dto.command.GenerateJwtTokenCommand;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.expiration.time}")
    private long expirationTime;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public String generateToken(GenerateJwtTokenCommand generateTokenCommand)  {
        return Jwts.builder()
                .setSubject(generateTokenCommand.getTokenId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), SignatureAlgorithm.HS256)
                .compact();
    }
}
