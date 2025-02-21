package com.enigma.livecode_loan_app.service.impl;

import com.enigma.livecode_loan_app.entity.LoanTransactionDetail;
import com.enigma.livecode_loan_app.repository.LoanTransactionDetailRepository;
import com.enigma.livecode_loan_app.service.LoanTransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class LoanTransactionDetailServiceImpl implements LoanTransactionDetailService {
    private final LoanTransactionDetailRepository loanTransactionDetailRepository;

    @Override
    public List<LoanTransactionDetail> saveBulk(List<LoanTransactionDetail> transactionDetails) {
        return loanTransactionDetailRepository.saveAllAndFlush(transactionDetails);
    }

    @Override
    public List<LoanTransactionDetail> saveOne(LoanTransactionDetail transactionDetail) {
        return loanTransactionDetailRepository.saveAllAndFlush(List.of(transactionDetail));
    }
}
