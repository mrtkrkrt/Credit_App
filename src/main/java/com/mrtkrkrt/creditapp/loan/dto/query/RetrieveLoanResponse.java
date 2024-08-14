package com.mrtkrkrt.creditapp.loan.dto.query;

import com.mrtkrkrt.creditapp.loan.dto.LoanDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetrieveLoanResponse {
    private List<LoanDto> loans;
}
