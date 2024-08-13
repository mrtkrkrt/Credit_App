package com.mrtkrkrt.creditapp.loan.api.command;

import com.mrtkrkrt.creditapp.loan.dto.command.TakeOutInstallmentCommand;
import com.mrtkrkrt.creditapp.loan.dto.command.TakeOutInstallmentServiceCommand;
import com.mrtkrkrt.creditapp.loan.service.command.InstallmentCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            @RequestHeader("x-user-tckn") String tckn,
            @RequestBody @Valid TakeOutInstallmentCommand takeOutInstallmentCommand) {
        installmentCommandService.takeOutInstallment(TakeOutInstallmentServiceCommand.builder()
                .amount(takeOutInstallmentCommand.getAmount())
                .loanId(takeOutInstallmentCommand.getLoanId())
                .tckn(tckn)
                .build());
        return ResponseEntity.ok().build();
    }
}
