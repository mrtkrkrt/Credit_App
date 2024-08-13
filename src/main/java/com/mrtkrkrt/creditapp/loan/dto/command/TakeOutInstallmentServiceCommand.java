package com.mrtkrkrt.creditapp.loan.dto.command;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TakeOutInstallmentServiceCommand {
    private Long loanId;
    private BigDecimal amount;
    private String tckn;
}
