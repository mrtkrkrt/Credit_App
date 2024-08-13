package com.mrtkrkrt.creditapp.user.service.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrtkrkrt.creditapp.user.model.UserElastic;
import com.mrtkrkrt.creditapp.user.repository.UserElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticUserSyncConsumer {

    private final ObjectMapper objectMapper;
    private final UserElasticRepository userElasticRepository;

    @KafkaListener(topics = "user-elastic-sync", groupId = "group-id")
    @Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void listenUserInsert(String message) {
        log.info("UserEventListener -> listenUserInsert is started with message: {}", message);
        try {
            UserElastic user = objectMapper.readValue(message, UserElastic.class);
            userElasticRepository.save(user);
            log.info("UserEventListener -> listenUserInsert is finished with user tckn: {}", user.getTckn());
        } catch (Exception e) {
            log.error("UserEventListener -> listenUserInsert is failed with message: {}", message, e);
        }
    }
}
