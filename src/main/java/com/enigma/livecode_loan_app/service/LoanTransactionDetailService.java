package com.enigma.livecode_loan_app.service;

import com.enigma.livecode_loan_app.entity.LoanTransactionDetail;

import java.util.List;

public interface LoanTransactionDetailService {
    List<LoanTransactionDetail> saveBulk (List<LoanTransactionDetail> transactionDetails);
    List<LoanTransactionDetail> saveOne (LoanTransactionDetail transactionDetails);
}
