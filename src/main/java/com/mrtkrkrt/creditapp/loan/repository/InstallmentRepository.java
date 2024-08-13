package com.mrtkrkrt.creditapp.loan.repository;

import com.mrtkrkrt.creditapp.loan.model.Installment;
import com.mrtkrkrt.creditapp.loan.model.enums.InstallmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {
    List<Installment> findByLoanIdAndStatus(Long loanId, InstallmentStatus installmentStatus);
}
