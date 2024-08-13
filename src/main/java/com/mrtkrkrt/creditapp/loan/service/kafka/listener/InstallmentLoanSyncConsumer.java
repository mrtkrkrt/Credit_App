package com.mrtkrkrt.creditapp.loan.service.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrtkrkrt.creditapp.loan.model.InstallmentElastic;
import com.mrtkrkrt.creditapp.loan.repository.InstallmentElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstallmentLoanSyncConsumer {

    private final ObjectMapper objectMapper;
    private final InstallmentElasticRepository installmentElasticRepository;

    @KafkaListener(topics = "installment-elastic-sync", groupId = "group-id")
    @Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void listenInstallmentInsert(String message) {
        log.info("InstallmentEventListener -> installmentLoanInsert is started with message: {}", message);
        try {
            InstallmentElastic installmentElastic = objectMapper.readValue(message, InstallmentElastic.class);
            installmentElasticRepository.save(installmentElastic);
            log.info("InstallmentEventListener -> installmentLoanInsert is finished with amount: {}", installmentElastic.getAmount());
        } catch (Exception e) {
            log.error("InstallmentEventListener -> installmentLoanInsert is failed with message: {}", message, e);
        }
    }
}
