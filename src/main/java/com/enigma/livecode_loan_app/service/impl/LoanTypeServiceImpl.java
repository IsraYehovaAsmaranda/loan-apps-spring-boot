package com.enigma.livecode_loan_app.service.impl;

import com.enigma.livecode_loan_app.entity.InstalmentType;
import com.enigma.livecode_loan_app.entity.LoanType;
import com.enigma.livecode_loan_app.repository.LoanTypeRepository;
import com.enigma.livecode_loan_app.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanTypeServiceImpl implements LoanTypeService {
    private final LoanTypeRepository loanTypeRepository;


    @Override
    public LoanType createLoanType(LoanType loanType) {
        return loanTypeRepository.saveAndFlush(loanType);
    }

    @Override
    public LoanType getById(String id) {
        return findByIdOrThrow(id);
    }

    @Override
    public List<LoanType> getAll() {
        return loanTypeRepository.findAll();
    }

    @Override
    public LoanType update(LoanType loanType) {
        findByIdOrThrow(loanType.getId());
        return loanTypeRepository.saveAndFlush(loanType);
    }

    @Override
    public void delete(String id) {
        findByIdOrThrow(id);
        loanTypeRepository.deleteById(id);
    }

    private LoanType findByIdOrThrow(String id) {
        return loanTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "INSTALLMENT TYPE NOT FOUND"));
    }
}
