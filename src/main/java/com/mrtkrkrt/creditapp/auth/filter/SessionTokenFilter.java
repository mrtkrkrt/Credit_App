package com.mrtkrkrt.creditapp.auth.filter;

import static com.mrtkrkrt.creditapp.common.constants.CommonHeaderConstants.X_USER_TCKN;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrtkrkrt.creditapp.auth.dto.command.GenerateTokenCommand;
import com.mrtkrkrt.creditapp.auth.filter.service.redis.AuthRedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
@Order(2)
public class SessionTokenFilter extends OncePerRequestFilter {

    private final AuthRedisService<GenerateTokenCommand> redisService;
    private final ObjectMapper objectMapper;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        // public url'leri bir map in içinden al
        if (request.getRequestURI().contains("/register") || request.getRequestURI().contains("/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        String sessionToken = request.getHeader("Authorization");
        if (sessionToken == null || sessionToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String tckn = tokenIsAuthorized(sessionToken, request);
        if (tckn == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        MDC.put(X_USER_TCKN, tckn);
        filterChain.doFilter(request, response);
    }


    private String tokenIsAuthorized(String sessionToken, HttpServletRequest request) throws JsonProcessingException {
        if (!sessionToken.startsWith("Bearer ")) {
            return null;
        }

        Claims claims = null;
        try {
            claims = extractClaims(sessionToken.substring(7));
        } catch (ExpiredJwtException e) {
            return null;
        }
        GenerateTokenCommand generateTokenCommand = redisService.getValue(claims.getSubject());
        if (checkSessionTokenId(claims.getSubject()) || generateTokenCommand != null) {
            return generateTokenCommand.getTckn();
        }
        return null;
    }

    private boolean checkSessionTokenId(String redisSessionTokenId) throws JsonProcessingException {
        if (redisSessionTokenId == null || redisSessionTokenId.isEmpty()) {
            return false;
        }
        GenerateTokenCommand command = redisService.getValue(redisSessionTokenId);
        return command != null && !command.getTckn().isEmpty();
    }

    public Claims extractClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private String getUserTckn(String sessionToken) {
        if (!sessionToken.startsWith("Bearer ")) {
            return null;
        }

        Claims claims = null;
        try {
            claims = extractClaims(sessionToken.substring(7));
        } catch (ExpiredJwtException e) {
            return null;
        }
        return redisService.getValue(claims.getSubject()).getTckn();
    }
}
