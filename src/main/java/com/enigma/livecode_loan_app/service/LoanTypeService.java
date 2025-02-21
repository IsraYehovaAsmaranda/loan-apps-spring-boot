package com.enigma.livecode_loan_app.service;

import com.enigma.livecode_loan_app.entity.LoanType;

import java.util.List;

public interface LoanTypeService {
    LoanType createLoanType(LoanType loanType);
    LoanType getById(String id);
    List<LoanType> getAll();
    LoanType update(LoanType loanType);
    void delete(String id);
}
