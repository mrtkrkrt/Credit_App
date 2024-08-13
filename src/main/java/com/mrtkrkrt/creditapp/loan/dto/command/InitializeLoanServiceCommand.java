package com.mrtkrkrt.creditapp.loan.dto.command;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializeLoanServiceCommand {
    private BigDecimal amount;
    private int installmentCount;
    private BigDecimal interestRate;
    private String tckn;
}
