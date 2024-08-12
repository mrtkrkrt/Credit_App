package com.mrtkrkrt.creditapp.loan.repository;

import com.mrtkrkrt.creditapp.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
