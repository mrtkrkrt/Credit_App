package com.mrtkrkrt.creditapp.loan.model;

import com.mrtkrkrt.creditapp.loan.model.enums.InstallmentStatus;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private int dueDay;
    private BigDecimal interestRate;
    @Field(type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS", format = {})
    private LocalDateTime dueDate;
}
