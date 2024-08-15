package com.mrtkrkrt.creditapp.loan.service.command;

import com.mrtkrkrt.creditapp.common.exception.ErrorCode;
import com.mrtkrkrt.creditapp.common.exception.GenericException;
import com.mrtkrkrt.creditapp.loan.dto.command.InitializeInstallmentCommand;
import com.mrtkrkrt.creditapp.loan.dto.command.TakeOutInstallmentServiceCommand;
import com.mrtkrkrt.creditapp.loan.dto.query.InitializeInstallmentResponse;
import com.mrtkrkrt.creditapp.loan.model.Installment;
import com.mrtkrkrt.creditapp.loan.model.InstallmentElastic;
import com.mrtkrkrt.creditapp.loan.model.InstallmentRdmsSyncData;
import com.mrtkrkrt.creditapp.loan.model.enums.InstallmentStatus;
import com.mrtkrkrt.creditapp.loan.repository.InstallmentRepository;
import com.mrtkrkrt.creditapp.loan.service.kafka.publisher.InstallmentEventPublisher;
import com.mrtkrkrt.creditapp.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstallmentCommandService {

    private final InstallmentEventPublisher installmentEventPublisher;
    private final InstallmentRepository installmentRepository;

    public InitializeInstallmentResponse createInstallments(InitializeInstallmentCommand initializeInstallmentCommand) {
        log.info("InstallmentCommandService -> createInstallments is started, initializeInstallmentCommand: {}", initializeInstallmentCommand);

        ArrayList<Installment> installments = new ArrayList<>();
        for (int i = 0; i < initializeInstallmentCommand.getInstallmentCount(); i++) {
            installments.add(Installment.builder()
                    .amount(calculateInstallmentAmount(initializeInstallmentCommand))
                    .status(InstallmentStatus.UNPAID)
                    .dueDate(getDueDate(i))
                    .build());
        }

        return InitializeInstallmentResponse.builder()
                .installments(installments)
                .build();
    }

    private static LocalDateTime getDueDate(int i) {
        LocalDateTime dueDate = LocalDateTime.now().plusMonths(i + 1);
        return dueDate.getDayOfWeek().equals("SATURDAY") ? dueDate.plusDays(2) : dueDate.getDayOfWeek().equals("SUNDAY") ? dueDate.plusDays(1) : dueDate;
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
                        .dueDay(installments.get(i).getDueDay())
                        .dueDate(installments.get(i).getDueDate())
                        .interestRate(installments.get(i).getLoan().getInterestRate())
                        .build());
            } catch (Exception e) {
                log.error("InstallmentCommandService -> syncElasticInstallments is failed, installment: {}", installments.get(i), e);
            }
        }
    }

    public void takeOutInstallment(TakeOutInstallmentServiceCommand takeOutInstallmentServiceCommand) {
        log.info("InstallmentCommandService -> takeOutInstallment is started, takeOutInstallmentServiceCommand: {}", takeOutInstallmentServiceCommand);
        checkLoanHasUnPaidInstallment(takeOutInstallmentServiceCommand.getLoanId());
        checkAmountIsEnoughForInstallment(takeOutInstallmentServiceCommand.getAmount(), takeOutInstallmentServiceCommand.getLoanId());
        updateInstallmentStatus(takeOutInstallmentServiceCommand.getLoanId());
    }

    private void updateInstallmentStatus(Long loanId) {
        log.info("InstallmentCommandService -> updateInstallmentStatus is started, loanId: {}", loanId);
        Installment installment = installmentRepository.findByLoanIdAndStatus(loanId, InstallmentStatus.UNPAID).stream().findFirst().get();
        installment.setStatus(InstallmentStatus.PAID);
        installmentRepository.save(installment);
        installmentEventPublisher.publish("installment-elastic-sync", InstallmentElastic.builder()
                .id(installment.getId())
                .amount(installment.getAmount())
                .status(installment.getStatus())
                .loanId(loanId)
                .dueDay(installment.getDueDay())
                .interestRate(installment.getLoan().getInterestRate())
                .build());
        installmentEventPublisher.publish("loan-installment-paid", installment.getLoan().getId());
    }

    private void checkAmountIsEnoughForInstallment(BigDecimal amount, Long loanId) {
        log.info("InstallmentCommandService -> checkAmountIsEnoughForInstallment is started, amount: {}, loanId: {}", amount, loanId);
        if (installmentRepository.findByLoanIdAndStatus(loanId, InstallmentStatus.UNPAID).stream().findFirst().get().getAmount().compareTo(amount) != 0) {
            throw GenericException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .logMessage(this.getClass().getName() + " -> checkAmountIsEnoughForInstallment -> Amount is not enough for installment")
                    .message(ErrorCode.AMOUNT_IS_NOT_ENOUGH_FOR_INSTALLMENT)
                    .build();
        }
    }

    private void checkLoanHasUnPaidInstallment(Long loanId) {
        log.info("InstallmentCommandService -> checkLoanHasUnPaidInstallment is started, loanId: {}", loanId);
        if (installmentRepository.findByLoanIdAndStatus(loanId, InstallmentStatus.UNPAID).isEmpty()) {
            throw GenericException.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .logMessage(this.getClass().getName() + " -> checkLoanHasUnPaidInstallment -> Loan has no unpaid installment")
                    .message(ErrorCode.LOAN_HAS_NO_UNPAID_INSTALLMENT)
                    .build();
        }
    }

    public void updateInstallment(InstallmentRdmsSyncData installment) {
        log.info("InstallmentCommandService -> updateInstallment is started, installment: {}", installment);
        Installment installmentEntity = installmentRepository.findById(installment.getId()).get();
        installmentEntity.setAmount(installment.getAmount());
        installmentEntity.setDueDay(installment.getDueDay());
        installmentRepository.save(installmentEntity);
    }
}
