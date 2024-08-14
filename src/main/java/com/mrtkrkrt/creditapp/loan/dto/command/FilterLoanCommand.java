package com.mrtkrkrt.creditapp.loan.dto.command;

import com.mrtkrkrt.creditapp.loan.model.enums.LoanStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterLoanCommand {
    private LoanStatus status;
}
