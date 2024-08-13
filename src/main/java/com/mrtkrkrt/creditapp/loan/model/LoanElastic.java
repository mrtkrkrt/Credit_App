package com.mrtkrkrt.creditapp.loan.model;

import com.mrtkrkrt.creditapp.loan.model.enums.LoanStatus;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

@Document(indexName = "loan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanElastic {
    @Id
    private Long id;
    private LoanStatus status;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private int installmentCount;
    private Long userId;
}
