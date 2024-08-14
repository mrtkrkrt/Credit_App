package com.mrtkrkrt.creditapp.loan.dto.query;

import com.mrtkrkrt.creditapp.loan.model.InstallmentElastic;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetrieveInstallmentResponse {
    private List<InstallmentElastic> installments;
}
