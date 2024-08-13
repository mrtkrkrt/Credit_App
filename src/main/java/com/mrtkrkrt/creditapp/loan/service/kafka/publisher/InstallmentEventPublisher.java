package com.mrtkrkrt.creditapp.loan.service.kafka.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstallmentEventPublisher {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String topic, Object event) {
        try {
            log.info("LoanEventPublisher -> publish is started, event: {}", event);
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic, message);
            log.info("LoanEventPublisher -> publish is finished, event: {}", event);
        } catch (Exception e) {
            log.error("LoanEventPublisher -> publish is failed, event: {}", event, e);
            throw new RuntimeException(e);
        }
    }

}
