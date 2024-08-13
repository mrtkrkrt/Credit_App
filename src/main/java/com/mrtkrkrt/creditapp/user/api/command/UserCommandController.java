package com.mrtkrkrt.creditapp.user.api.command;

import com.mrtkrkrt.creditapp.user.dto.command.CreateUserCommand;
import com.mrtkrkrt.creditapp.user.service.command.UserCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserCommandController {

    private final UserCommandService userCommandService;

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserCommand createUserCommand) {
        log.info("UserCommandController -> createUser is started, createUserCommand: {}", createUserCommand);
        userCommandService.createUser(createUserCommand.toItem());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
