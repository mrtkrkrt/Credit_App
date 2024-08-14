package com.mrtkrkrt.creditapp.loan.dto.command;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetrieveLoanServiceCommand {
    private String tckn;
}
