package com.mrtkrkrt.creditapp.loan.service.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrtkrkrt.creditapp.loan.model.LoanElastic;
import com.mrtkrkrt.creditapp.loan.repository.LoanElasticRepository;
import com.mrtkrkrt.creditapp.loan.service.command.LoanCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticLoanSyncConsumer {

    private final ObjectMapper objectMapper;
    private final LoanElasticRepository userElasticRepository;
    private final LoanCommandService loanCommandService;

    @KafkaListener(topics = "loan-elastic-sync", groupId = "group-id")
    @Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void listenLoanInsert(String message) {
        log.info("LoanEventListener -> listenLoanInsert is started with message: {}", message);
        try {
            LoanElastic loanElastic = objectMapper.readValue(message, LoanElastic.class);
            userElasticRepository.save(loanElastic);
            log.info("LoanEventListener -> listenLoanInsert is finished with amount: {}", loanElastic.getAmount());
        } catch (Exception e) {
            log.error("LoanEventListener -> listenLoanInsert is failed with message: {}", message, e);
        }
    }

    @KafkaListener(topics = "loan-installment-paid", groupId = "group-id")
    @Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void listenInstallmentPaid(String message) {
        log.info("LoanEventListener -> listenInstallmentPaid is started with message: {}", message);
        try {
            Long loanId = objectMapper.readValue(message, Long.class);
            loanCommandService.updateLoanInstallmentStatus(loanId);
            log.info("LoanEventListener -> listenInstallmentPaid is finished with message: {}", message);
        } catch (Exception e) {
            log.error("LoanEventListener -> listenInstallmentPaid is failed with message: {}", message, e);
        }
    }
}
