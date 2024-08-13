package com.mrtkrkrt.creditapp.loan.model;

import com.mrtkrkrt.creditapp.loan.model.enums.InstallmentStatus;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

@Document(indexName = "installment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstallmentElastic {
    @Id
    private Long id;
    private BigDecimal amount;
    private InstallmentStatus status;
    private Long loanId;
}
