package com.mrtkrkrt.creditapp.loan.api.command;

import static com.mrtkrkrt.creditapp.common.constants.CommonHeaderConstants.X_USER_TCKN;

import com.mrtkrkrt.creditapp.loan.dto.command.TakeOutInstallmentCommand;
import com.mrtkrkrt.creditapp.loan.dto.command.TakeOutInstallmentServiceCommand;
import com.mrtkrkrt.creditapp.loan.service.command.InstallmentCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/installment")
@RequiredArgsConstructor
@RestController
@Slf4j
public class InstallmentCommandController {

    private final InstallmentCommandService installmentCommandService;

    @PostMapping("/take-out")
    public ResponseEntity<Void> takeOutInstallment(
        @RequestBody @Valid TakeOutInstallmentCommand takeOutInstallmentCommand) {
        installmentCommandService.takeOutInstallment(TakeOutInstallmentServiceCommand.builder()
                .amount(takeOutInstallmentCommand.getAmount())
                .loanId(takeOutInstallmentCommand.getLoanId())
                .tckn(MDC.get(X_USER_TCKN))
                .build());
        return ResponseEntity.ok().build();
    }
}
