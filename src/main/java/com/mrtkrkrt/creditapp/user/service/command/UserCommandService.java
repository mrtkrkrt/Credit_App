package com.mrtkrkrt.creditapp.user.service.command;

import com.mrtkrkrt.creditapp.common.exception.ErrorCode;
import com.mrtkrkrt.creditapp.common.exception.GenericException;
import com.mrtkrkrt.creditapp.user.model.User;
import com.mrtkrkrt.creditapp.user.repository.UserRepository;
import com.mrtkrkrt.creditapp.user.service.kafka.publisher.UserEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventPublisher userEventPublisher;

    public void createUser(User user) {
        isUserExists(user.getTckn());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        userEventPublisher.publish("user-elastic-sync", user);
    }

    public void isUserExists(String tckn) {
        if (userRepository.existsByTckn(tckn)) {
            throw GenericException.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .logMessage(this.getClass().getName() + ".createUser() user already exists with tckn: {}", tckn)
                    .message(ErrorCode.USER_ALREADY_EXISTS)
                    .build();
        }
    }
}
