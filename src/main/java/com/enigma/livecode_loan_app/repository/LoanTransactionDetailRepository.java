package com.enigma.livecode_loan_app.repository;

import com.enigma.livecode_loan_app.entity.LoanTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTransactionDetailRepository extends JpaRepository<LoanTransactionDetail, String> {
}
