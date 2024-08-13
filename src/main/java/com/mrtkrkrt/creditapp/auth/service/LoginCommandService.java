package com.mrtkrkrt.creditapp.auth.service;

import com.mrtkrkrt.creditapp.auth.dto.command.GenerateTokenCommand;
import com.mrtkrkrt.creditapp.auth.dto.command.LoginCommand;
import com.mrtkrkrt.creditapp.auth.dto.query.LoginResponse;
import com.mrtkrkrt.creditapp.auth.service.token.TokenService;
import com.mrtkrkrt.creditapp.user.UserService;
import com.mrtkrkrt.creditapp.user.model.UserElastic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginCommandService {

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginCommand loginCommand) {
        log.info("LoginCommandService -> login is started, loginCommand: {}", loginCommand);
        UserElastic userByTckn = userService.getUserByTckn(loginCommand.getTckn());
        passwordEncoder.matches(loginCommand.getPassword(), userByTckn.getPassword());
        return LoginResponse.builder().token(tokenService.generateToken(GenerateTokenCommand.builder().tckn(loginCommand.getTckn()).build())).build();
    }
}
