package com.mrtkrkrt.creditapp.loan.dto.command;

import com.mrtkrkrt.creditapp.loan.model.Loan;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializeInstallmentCommand {
    private BigDecimal amount;
    private BigDecimal interestRate;
    private int installmentCount;
    private Loan loan;
}
