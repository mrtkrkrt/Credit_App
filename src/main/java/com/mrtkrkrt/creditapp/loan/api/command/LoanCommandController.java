package com.mrtkrkrt.creditapp.loan.api.command;

import static com.mrtkrkrt.creditapp.common.constants.CommonHeaderConstants.X_USER_TCKN;

import com.mrtkrkrt.creditapp.loan.dto.command.InitializeLoanCommand;
import com.mrtkrkrt.creditapp.loan.dto.command.InitializeLoanServiceCommand;
import com.mrtkrkrt.creditapp.loan.service.command.LoanCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/loan")
@RequiredArgsConstructor
@RestController
@Slf4j
public class LoanCommandController {

    private final LoanCommandService loanCommandService;

    @PostMapping("/")
    public ResponseEntity<Void> takeOutLoan(
            @RequestBody @Valid InitializeLoanCommand initializeLoanCommand) {
        loanCommandService.takeOutLoan(InitializeLoanServiceCommand.builder()
                .interestRate(initializeLoanCommand.getInterestRate())
                .installmentCount(initializeLoanCommand.getInstallmentCount())
                .amount(initializeLoanCommand.getAmount())
                .tckn(MDC.get(X_USER_TCKN))
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
