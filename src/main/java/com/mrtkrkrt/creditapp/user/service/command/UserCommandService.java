package com.mrtkrkrt.creditapp.user.service.command;

import com.mrtkrkrt.creditapp.user.model.User;
import com.mrtkrkrt.creditapp.user.repository.UserRepository;
import com.mrtkrkrt.creditapp.user.service.kafka.publisher.UserEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        userEventPublisher.publish("user-elastic-sync", user);
    }

    public boolean isUserExists(String tckn) {
        return userRepository.existsByTckn(tckn);
    }
}
