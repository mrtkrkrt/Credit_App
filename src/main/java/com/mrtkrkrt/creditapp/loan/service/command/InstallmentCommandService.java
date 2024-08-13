package com.mrtkrkrt.creditapp.loan.service.command;

import com.mrtkrkrt.creditapp.loan.dto.command.InitializeInstallmentCommand;
import com.mrtkrkrt.creditapp.loan.dto.query.InitializeInstallmentResponse;
import com.mrtkrkrt.creditapp.loan.model.Installment;
import com.mrtkrkrt.creditapp.loan.model.InstallmentElastic;
import com.mrtkrkrt.creditapp.loan.model.enums.InstallmentStatus;
import com.mrtkrkrt.creditapp.loan.service.kafka.publisher.InstallmentEventPublisher;
import com.mrtkrkrt.creditapp.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstallmentCommandService {

    private final InstallmentEventPublisher installmentEventPublisher;

    public InitializeInstallmentResponse createInstallments(InitializeInstallmentCommand initializeInstallmentCommand) {
        log.info("InstallmentCommandService -> createInstallments is started, initializeInstallmentCommand: {}", initializeInstallmentCommand);

        ArrayList<Installment> installments = new ArrayList<>();
        for (int i = 0; i < initializeInstallmentCommand.getInstallmentCount(); i++) {
            installments.add(Installment.builder()
                    .amount(calculateInstallmentAmount(initializeInstallmentCommand))
                    .status(InstallmentStatus.UNPAID)
                    .build());
        }

        return InitializeInstallmentResponse.builder()
                .installments(installments)
                .build();
    }

    private BigDecimal calculateInstallmentAmount(InitializeInstallmentCommand initializeInstallmentCommand) {
        return initializeInstallmentCommand.getAmount().divide(BigDecimal.valueOf(initializeInstallmentCommand.getInstallmentCount()));
    }

    public void syncElasticInstallments(List<Installment> installments, User user) {
        log.info("InstallmentCommandService -> syncElasticInstallments is started, installments: {}", installments);
        for (int i = 0; i < installments.size(); i++) {
            try {
                installmentEventPublisher.publish("installment-elastic-sync", InstallmentElastic.builder()
                        .id(user.getLoans().get(user.getLoans().size() - 1).getInstallments().get(i).getId())
                        .amount(installments.get(i).getAmount())
                        .status(installments.get(i).getStatus())
                        .loanId(user.getLoans().get(user.getLoans().size() - 1).getId())
                        .build());
            } catch (Exception e) {
                log.error("InstallmentCommandService -> syncElasticInstallments is failed, installment: {}", installments.get(i), e);
            }
        }
    }
}
