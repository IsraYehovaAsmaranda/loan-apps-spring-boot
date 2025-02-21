package com.enigma.livecode_loan_app.service;

import com.enigma.livecode_loan_app.model.request.ApproveTransactionRequest;
import com.enigma.livecode_loan_app.model.request.NewLoanTransactionRequest;
import com.enigma.livecode_loan_app.model.request.PayLoanTransactionRequest;
import com.enigma.livecode_loan_app.model.response.LoanTransactionResponse;

public interface LoanTransactionService {
    LoanTransactionResponse requestLoan(NewLoanTransactionRequest request);
    LoanTransactionResponse getById(String id);
    LoanTransactionResponse approveTransaction(ApproveTransactionRequest request, String adminId);
    void payInstalment (String id, PayLoanTransactionRequest request);
}
