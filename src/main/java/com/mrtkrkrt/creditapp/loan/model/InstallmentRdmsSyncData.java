package com.mrtkrkrt.creditapp.loan.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstallmentRdmsSyncData {
    private Long id;
    private BigDecimal amount;
    private int dueDay;
}
