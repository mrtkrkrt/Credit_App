package com.mrtkrkrt.creditapp.auth.api;

import com.mrtkrkrt.creditapp.auth.dto.command.LoginCommand;
import com.mrtkrkrt.creditapp.auth.dto.query.LoginResponse;
import com.mrtkrkrt.creditapp.auth.service.LoginCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
@RestController
@Slf4j
public class LoginCommandController {

    private final LoginCommandService loginCommandService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginCommand loginCommand) {
        log.info("LoginCommandController -> login is started, loginCommand: {}", loginCommand);
        return ResponseEntity.status(HttpStatus.OK).body(loginCommandService.login(loginCommand));
    }
}
