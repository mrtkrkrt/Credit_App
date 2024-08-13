package com.mrtkrkrt.creditapp.loan.api.command;

import com.mrtkrkrt.creditapp.loan.dto.command.InitializeLoanCommand;
import com.mrtkrkrt.creditapp.loan.dto.command.InitializeLoanServiceCommand;
import com.mrtkrkrt.creditapp.loan.service.command.LoanCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            @RequestHeader("x-user-tckn") String tckn,
            @RequestBody @Valid InitializeLoanCommand initializeLoanCommand) {
        loanCommandService.takeOutLoan(InitializeLoanServiceCommand.builder()
                .interestRate(initializeLoanCommand.getInterestRate())
                .installmentCount(initializeLoanCommand.getInstallmentCount())
                .amount(initializeLoanCommand.getAmount())
                .tckn(tckn)
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
