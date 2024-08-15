package com.mrtkrkrt.creditapp.loan.service.query;

import com.mrtkrkrt.creditapp.loan.dto.query.RetrieveInstallmentResponse;
import com.mrtkrkrt.creditapp.loan.dto.command.RetrieveInstallmentsServiceCommand;
import com.mrtkrkrt.creditapp.loan.model.InstallmentRdmsSyncData;
import com.mrtkrkrt.creditapp.loan.model.enums.InstallmentStatus;
import com.mrtkrkrt.creditapp.loan.repository.InstallmentElasticRepository;
import com.mrtkrkrt.creditapp.loan.service.kafka.publisher.InstallmentEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstallmentQueryService {

    private final InstallmentElasticRepository installmentElasticRepository;
    private final InstallmentEventPublisher installmentEventPublisher;

    public RetrieveInstallmentResponse retrieveAllInstallments(RetrieveInstallmentsServiceCommand build) {
        return RetrieveInstallmentResponse.builder().installments(installmentElasticRepository.findByLoanId(build.getLoanId())).build();
    }

    public void scanInstallment() {
        log.info("InstallmentQueryService -> scanInstallment is started");
        installmentElasticRepository.findByDueDateLessThanEqualAndStatusIsNot(LocalDateTime.now(), InstallmentStatus.PAID)
                .forEach(installmentElastic -> {
                    installmentElastic.setDueDay(installmentElastic.getDueDay() + 1);
                    installmentElasticRepository.save(installmentElastic);
                    installmentEventPublisher.publish("installment-rdbms-sync", InstallmentRdmsSyncData.builder()
                            .id(installmentElastic.getId())
                            .amount(((installmentElastic.getInterestRate().divide(BigDecimal.valueOf(100)))
                                    .multiply(installmentElastic.getAmount())
                                    .multiply(BigDecimal.valueOf(installmentElastic.getDueDay()))))
                            .dueDay(installmentElastic.getDueDay())
                            .build());
                });
    }
}










