package com.mrtkrkrt.creditapp.loan.model;

import com.mrtkrkrt.creditapp.common.model.BaseEntity;
import com.mrtkrkrt.creditapp.loan.model.enums.LoanStatus;
import com.mrtkrkrt.creditapp.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private BigDecimal initialAmount;
    private BigDecimal interestRate;
    private int installmentCount;
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Installment> installments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addInstallment(Installment installment) {
        this.getInstallments().add(installment);
        installment.setLoan(this);
    }

    public List<Installment> getInstallments() {
        if (this.installments == null)
            this.installments = new ArrayList<>();
        return this.installments;
    }
}
