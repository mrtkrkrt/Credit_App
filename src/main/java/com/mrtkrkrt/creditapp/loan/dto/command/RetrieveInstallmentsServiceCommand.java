package com.mrtkrkrt.creditapp.loan.dto.command;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetrieveInstallmentsServiceCommand {
    private String loanId;
}
