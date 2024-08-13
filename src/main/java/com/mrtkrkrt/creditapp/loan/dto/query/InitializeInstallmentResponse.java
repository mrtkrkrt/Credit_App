package com.mrtkrkrt.creditapp.loan.dto.query;

import com.mrtkrkrt.creditapp.loan.model.Installment;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializeInstallmentResponse {
    private List<Installment> installments;
}
