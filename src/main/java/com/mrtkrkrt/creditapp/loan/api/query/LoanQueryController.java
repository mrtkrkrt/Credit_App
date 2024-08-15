package com.mrtkrkrt.creditapp.loan.api.query;

import static com.mrtkrkrt.creditapp.common.constants.CommonHeaderConstants.X_USER_TCKN;

import com.mrtkrkrt.creditapp.loan.dto.command.FilterLoanCommand;
import com.mrtkrkrt.creditapp.loan.dto.command.FilterLoanServiceCommand;
import com.mrtkrkrt.creditapp.loan.dto.query.RetrieveInstallmentResponse;
import com.mrtkrkrt.creditapp.loan.dto.command.RetrieveInstallmentsServiceCommand;
import com.mrtkrkrt.creditapp.loan.dto.query.RetrieveLoanResponse;
import com.mrtkrkrt.creditapp.loan.dto.command.RetrieveLoanServiceCommand;
import com.mrtkrkrt.creditapp.loan.service.query.InstallmentQueryService;
import com.mrtkrkrt.creditapp.loan.service.query.LoanQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/loan")
@RequiredArgsConstructor
@RestController
@Slf4j
public class LoanQueryController {

    private final LoanQueryService loanQueryService;
    private final InstallmentQueryService installmentQueryService;

    @GetMapping("/")
    public ResponseEntity<RetrieveLoanResponse> retrieveAllLoans() {
        log.info("Retrieving all loans");
        return ResponseEntity.ok(loanQueryService.retrieveAllLoans(RetrieveLoanServiceCommand.builder().tckn(MDC.get(X_USER_TCKN)).build()));
    }

    @GetMapping("/filter")
    public ResponseEntity<RetrieveLoanResponse> retrieveAllLoans(@RequestBody FilterLoanCommand filterLoanCommand) {
        log.info("Retrieving all loans with status: {}", filterLoanCommand.getStatus());
        return ResponseEntity.ok(loanQueryService.filterLoans(
            FilterLoanServiceCommand.builder().tckn(MDC.get(X_USER_TCKN)).status(filterLoanCommand.getStatus()).build()));
    }

    @GetMapping("/installments")
    public ResponseEntity<RetrieveInstallmentResponse> retrieveAllInstallments(
        @RequestHeader("x-loan-id") String loanId
    ) {
        log.info("Retrieving all installments");
        return ResponseEntity.ok(
            installmentQueryService.retrieveAllInstallments(RetrieveInstallmentsServiceCommand.builder().loanId(loanId).build()));
    }
}
