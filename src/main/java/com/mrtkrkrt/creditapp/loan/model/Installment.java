package com.mrtkrkrt.creditapp.loan.model;

import com.mrtkrkrt.creditapp.common.model.BaseEntity;
import com.mrtkrkrt.creditapp.loan.model.enums.InstallmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "installments")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Installment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private InstallmentStatus status;
    private LocalDateTime dueDate;
    private int dueDay;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_id")
    private Loan loan;

}
