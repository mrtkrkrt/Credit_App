package com.mrtkrkrt.creditapp.loan.service.command;

import com.mrtkrkrt.creditapp.loan.dto.command.InitializeInstallmentCommand;
import com.mrtkrkrt.creditapp.loan.dto.command.InitializeLoanServiceCommand;
import com.mrtkrkrt.creditapp.loan.model.Installment;
import com.mrtkrkrt.creditapp.loan.model.Loan;
import com.mrtkrkrt.creditapp.loan.model.LoanElastic;
import com.mrtkrkrt.creditapp.loan.model.enums.InstallmentStatus;
import com.mrtkrkrt.creditapp.loan.model.enums.LoanStatus;
import com.mrtkrkrt.creditapp.loan.repository.LoanRepository;
import com.mrtkrkrt.creditapp.loan.service.kafka.publisher.LoanEventPublisher;
import com.mrtkrkrt.creditapp.user.UserService;
import com.mrtkrkrt.creditapp.user.model.User;
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
    private final LoanEventPublisher loanEventPublisher;
    private final LoanRepository loanRepository;

    @Transactional
    public void takeOutLoan(InitializeLoanServiceCommand initializeLoanCommand) {
        Loan loan = Loan.builder()
                .status(LoanStatus.UNPAID)
                .amount(initializeLoanCommand.getAmount())
                .interestRate(initializeLoanCommand.getInterestRate())
                .installmentCount(initializeLoanCommand.getInstallmentCount())
                .build();
        createInstallments(initializeLoanCommand, loan);
        // TODO user coupling from another module???
        User user = userService.addLoan(initializeLoanCommand.getTckn(), loan);
        syncElasticLoan(loan, user);
        installmentCommandService.syncElasticInstallments(loan.getInstallments(), user);
    }

    private void syncElasticLoan(Loan loan, User user) {
        LoanElastic loanElastic = LoanElastic.builder()
                .id(user.getLoans().get(user.getLoans().size() - 1).getId())
                .status(loan.getStatus())
                .amount(loan.getAmount())
                .interestRate(loan.getInterestRate())
                .installmentCount(loan.getInstallmentCount())
                .userId(loan.getUser().getId())
                .build();
        loanEventPublisher.publish("loan-elastic-sync", loanElastic);
    }

    private void createInstallments(InitializeLoanServiceCommand initializeLoanCommand, Loan loan) {
        List<Installment> installments = installmentCommandService.createInstallments(InitializeInstallmentCommand.builder()
                .amount(initializeLoanCommand.getAmount())
                .interestRate(initializeLoanCommand.getInterestRate())
                .installmentCount(initializeLoanCommand.getInstallmentCount())
                .loan(loan)
                .build()).getInstallments();
        installments.forEach(loan::addInstallment);
    }

    public void updateLoanInstallmentStatus(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));
        if (loan.getInstallments().stream().filter(installment -> installment.getStatus().equals(InstallmentStatus.UNPAID)).toList().isEmpty()) {
            loan.setStatus(LoanStatus.PAID);
            loanRepository.save(loan);
            loanEventPublisher.publish("loan-elastic-sync", LoanElastic.builder()
                    .id(loan.getId())
                    .status(loan.getStatus())
                    .amount(loan.getAmount())
                    .interestRate(loan.getInterestRate())
                    .installmentCount(loan.getInstallmentCount())
                    .userId(loan.getUser().getId())
                    .build());
        }
    }
}
