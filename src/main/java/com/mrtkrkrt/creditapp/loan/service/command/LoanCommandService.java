package com.mrtkrkrt.creditapp.loan.service.command;

import com.mrtkrkrt.creditapp.loan.dto.command.InitializeInstallmentCommand;
import com.mrtkrkrt.creditapp.loan.dto.command.InitializeLoanServiceCommand;
import com.mrtkrkrt.creditapp.loan.dto.query.InitializeLoanResponse;
import com.mrtkrkrt.creditapp.loan.model.Installment;
import com.mrtkrkrt.creditapp.loan.model.Loan;
import com.mrtkrkrt.creditapp.loan.model.enums.LoanStatus;
import com.mrtkrkrt.creditapp.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanCommandService {

    private final UserService userService;
    private final InstallmentCommandService installmentCommandService;

    @Transactional
    public InitializeLoanResponse takeOutLoan(InitializeLoanServiceCommand initializeLoanCommand) {
        Loan loan = Loan.builder()
                .status(LoanStatus.UNPAID)
                .amount(initializeLoanCommand.getAmount())
                .interestRate(initializeLoanCommand.getInterestRate())
                .installmentCount(initializeLoanCommand.getInstallmentCount())
                .installments(List.of())
                .build();
        createInstallments(initializeLoanCommand, loan);
        userService.addLoan(initializeLoanCommand.getTckn(), loan);
        return InitializeLoanResponse.builder().build();
    }

    private void createInstallments(InitializeLoanServiceCommand initializeLoanCommand, Loan loan) {
        List<Installment> installments = installmentCommandService.createInstallments(InitializeInstallmentCommand.builder()
                .amount(initializeLoanCommand.getAmount())
                .interestRate(initializeLoanCommand.getInterestRate())
                .installmentCount(initializeLoanCommand.getInstallmentCount())
                .build()).getInstallments();
        installments.forEach(installment -> installment.setLoan(loan));
        loan.setInstallments(installments);
    }
}
