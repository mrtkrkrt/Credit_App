package com.mrtkrkrt.creditapp.loan.model;

import com.mrtkrkrt.creditapp.common.model.BaseEntity;
import com.mrtkrkrt.creditapp.loan.model.enums.LoanStatus;
import com.mrtkrkrt.creditapp.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "loan")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LoanStatus status;
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
