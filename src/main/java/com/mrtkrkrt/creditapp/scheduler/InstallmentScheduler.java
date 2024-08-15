package com.mrtkrkrt.creditapp.scheduler;

import com.mrtkrkrt.creditapp.scheduler.kafka.publisher.SchedulerEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstallmentScheduler {

    private final SchedulerEventPublisher schedulerEventPublisher;

    @Scheduled(cron = "*/60 * * * * *")
    public void publishInstallmentEvent() {
        log.info("Publishing installment event");
        schedulerEventPublisher.publish("installment-cron-batch", "");
    }
}
