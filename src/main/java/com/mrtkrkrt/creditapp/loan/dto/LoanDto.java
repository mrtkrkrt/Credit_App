package com.mrtkrkrt.creditapp.loan.dto;

import com.mrtkrkrt.creditapp.loan.model.InstallmentElastic;
import com.mrtkrkrt.creditapp.loan.model.enums.LoanStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDto {
    private Long creditId;
    private List<InstallmentElastic> installments;
    private LoanStatus status;
    private int installmentCount;
    private BigDecimal totalAmount;
}
