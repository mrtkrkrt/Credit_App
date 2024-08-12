package com.mrtkrkrt.creditapp.loan.model;

import com.mrtkrkrt.creditapp.loan.model.enums.InstallmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "installments")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Installment {

    @Id
    private Long id;
    private BigDecimal amount;
    private InstallmentStatus status;
    @OneToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
