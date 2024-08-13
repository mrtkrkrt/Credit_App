package com.mrtkrkrt.creditapp.loan.service.command;

import com.mrtkrkrt.creditapp.loan.dto.command.InitializeInstallmentCommand;
import com.mrtkrkrt.creditapp.loan.dto.query.InitializeInstallmentResponse;
import com.mrtkrkrt.creditapp.loan.model.Installment;
import com.mrtkrkrt.creditapp.loan.model.enums.InstallmentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstallmentCommandService {

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
        return initializeInstallmentCommand.getAmount().divide(BigDecimal.valueOf(initializeInstallmentCommand.getInstallmentCount())).multiply(BigDecimal.ONE.add(initializeInstallmentCommand.getInterestRate()));
    }
}
