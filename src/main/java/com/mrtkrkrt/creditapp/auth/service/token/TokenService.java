package com.mrtkrkrt.creditapp.auth.service.token;

import com.mrtkrkrt.creditapp.auth.dto.command.GenerateJwtTokenCommand;
import com.mrtkrkrt.creditapp.auth.dto.command.GenerateTokenCommand;
import com.mrtkrkrt.creditapp.auth.service.redis.RedisService;
import com.mrtkrkrt.creditapp.auth.service.token.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final RedisService<GenerateTokenCommand> redisService;
    private final JwtService jwtService;

    public String generateToken(GenerateTokenCommand generateTokenCommand)  {
        String redisTokenId = UUID.randomUUID().toString();
        redisService.putValue(redisTokenId, TimeUnit.MINUTES, 5 , generateTokenCommand);
        return jwtService.generateToken(GenerateJwtTokenCommand.builder().tokenId(redisTokenId).build());
    }
}
