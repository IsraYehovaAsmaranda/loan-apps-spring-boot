package com.enigma.livecode_loan_app.service.impl;

import com.enigma.livecode_loan_app.constant.ApprovalStatus;
import com.enigma.livecode_loan_app.constant.LoanStatus;
import com.enigma.livecode_loan_app.entity.*;
import com.enigma.livecode_loan_app.model.request.ApproveTransactionRequest;
import com.enigma.livecode_loan_app.model.request.NewLoanTransactionRequest;
import com.enigma.livecode_loan_app.model.request.PayLoanTransactionRequest;
import com.enigma.livecode_loan_app.model.response.CustomerResponse;
import com.enigma.livecode_loan_app.model.response.LoanTransactionDetailResponse;
import com.enigma.livecode_loan_app.model.response.LoanTransactionResponse;
import com.enigma.livecode_loan_app.model.response.UserResponse;
import com.enigma.livecode_loan_app.repository.LoanTransactionRepository;
import com.enigma.livecode_loan_app.service.*;
import com.enigma.livecode_loan_app.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanTransactionServiceImpl implements LoanTransactionService {
    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanTransactionDetailService loanTransactionDetailService;
    private final CustomerService customerService;
    private final InstalmentTypeService instalmentTypeService;
    private final LoanTypeService loanTypeService;
    private final AppUserService appUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoanTransactionResponse requestLoan(NewLoanTransactionRequest request) {
        LoanType foundLoanType = loanTypeService.getById(request.getLoanType().getId());
        InstalmentType foundInstalmentType = instalmentTypeService.getById(request.getInstalmentType().getId());
        CustomerResponse foundCustomerResponse = customerService.getById(request.getCustomer().getId());

        BigDecimal maxLoan = foundLoanType.getMaxLoan();

        if (maxLoan.doubleValue() > request.getNominal())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NOMINAL CANNOT BE BIGGER THAN MAX LOAN");

        Customer foundCustomer = Customer.builder()
                .id(foundCustomerResponse.getId())
                .firstName(foundCustomerResponse.getFirstName())
                .lastName(foundCustomerResponse.getLastName())
                .dateOfBirth(DateUtil.parseDate(foundCustomerResponse.getDateOfBirth(), "yyyy-MM-dd"))
                .phone(foundCustomerResponse.getPhone())
                .status(foundCustomerResponse.getStatus())
                .build();

        LoanTransaction loanTransaction = LoanTransaction.builder()
                .loanType(foundLoanType)
                .instalmentType(foundInstalmentType)
                .customer(foundCustomer)
                .nominal(request.getNominal())
                .createdAt(new Date().getTime())
                .updatedAt(new Date().getTime())
                .build();

        LoanTransaction savedLoanTransaction = loanTransactionRepository.saveAndFlush(loanTransaction);
        return LoanTransactionResponse.builder()
                .id(savedLoanTransaction.getId())
                .loanTypeId(savedLoanTransaction.getLoanType().getId())
                .instalmentTypeId(savedLoanTransaction.getInstalmentType().getId())
                .customerId(savedLoanTransaction.getCustomer().getId())
                .nominal(savedLoanTransaction.getNominal())
                .createdAt(savedLoanTransaction.getCreatedAt())
                .build();
    }

    @Override
    public LoanTransactionResponse getById(String id) {
        LoanTransaction loanTransaction = findByIdOrThrow(id);

        List<LoanTransactionDetailResponse> loanTransactionDetailResponses = toTransactionDetailResponses(loanTransaction);

        return toTransactionResponse(loanTransaction, loanTransactionDetailResponses);
    }

    private static LoanTransactionResponse toTransactionResponse(LoanTransaction loanTransaction, List<LoanTransactionDetailResponse> loanTransactionDetailResponses) {
        return LoanTransactionResponse.builder()
                .id(loanTransaction.getId())
                .loanTypeId(loanTransaction.getLoanType().getId())
                .instalmentTypeId(loanTransaction.getInstalmentType().getId())
                .customerId(loanTransaction.getCustomer().getId())
                .nominal(loanTransaction.getNominal())
                .approvedAt(loanTransaction.getApprovedAt())
                .approvedBy(loanTransaction.getApprovedBy())
                .approvalStatus(Optional.ofNullable(loanTransaction.getApprovalStatus())
                        .map(Enum::toString)
                        .orElse(null)
                )
                .transactionDetailResponses(loanTransactionDetailResponses)
                .createdAt(loanTransaction.getCreatedAt())
                .updatedAt(loanTransaction.getUpdatedAt())
                .build();
    }

    private static List<LoanTransactionDetailResponse> toTransactionDetailResponses(LoanTransaction loanTransaction) {
        return loanTransaction.getLoanTransactionDetails().stream().map(detail -> {
            return LoanTransactionDetailResponse.builder()
                    .id(detail.getId())
                    .transactionDate(detail.getTransactionDate())
                    .nominal(detail.getNominal())
                    .loanStatus(detail.getLoanStatus())
                    .createdAt(detail.getCreatedAt())
                    .updatedAt(detail.getUpdatedAt())
                    .build();
        }).toList();
    }

    private LoanTransaction findByIdOrThrow(String id) {
        return loanTransactionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TRANSACTION NOT FOUND"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoanTransactionResponse approveTransaction(ApproveTransactionRequest request, String adminId) {
        UserResponse foundUserResponse = appUserService.getById(adminId);

        LoanTransaction foundLoanTransaction = findByIdOrThrow(request.getLoanTransactionId());

        if(foundLoanTransaction.getApprovalStatus() != null && foundLoanTransaction.getApprovalStatus().equals(ApprovalStatus.APPROVED) )
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "LOAN TRANSACTIONS HAS ALREADY BEEN APPROVED");

        foundLoanTransaction.setApprovedAt(new Date().getTime());
        foundLoanTransaction.setApprovedBy(foundUserResponse.getEmail());
        foundLoanTransaction.setApprovalStatus(ApprovalStatus.APPROVED);
        foundLoanTransaction.setUpdatedAt(new Date().getTime());

        loanTransactionRepository.saveAndFlush(foundLoanTransaction);

        Integer numberOfMonths = foundLoanTransaction.getInstalmentType().getInstalmentType().getNumberOfMonths();
        double nominalPerMonth = foundLoanTransaction.getNominal() / numberOfMonths;
        LoanTransactionDetail newTransactionDetail = LoanTransactionDetail.builder()
                .transactionDate(new Date().getTime())
                .nominal(nominalPerMonth + (nominalPerMonth * request.getInterestRates()))
                .loanTransaction(foundLoanTransaction)
                .loanStatus(LoanStatus.PAID)
                .createdAt(new Date().getTime())
                .updatedAt(new Date().getTime())
                .build();

        List<LoanTransactionDetail> transactionDetails = loanTransactionDetailService.saveOne(newTransactionDetail);
        foundLoanTransaction.setLoanTransactionDetails(transactionDetails);

        List<LoanTransactionDetailResponse> transactionDetailResponses = toTransactionDetailResponses(foundLoanTransaction);

        return toTransactionResponse(foundLoanTransaction, transactionDetailResponses);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payInstalment(String id, PayLoanTransactionRequest request) {
        LoanTransaction loanTransaction = findByIdOrThrow(id);
        loanTransaction.setUpdatedAt(new Date().getTime());
        loanTransactionRepository.saveAndFlush(loanTransaction);

        List<LoanTransactionDetail> foundTransactionDetails = loanTransaction.getLoanTransactionDetails();

        Integer numberOfMonths = loanTransaction.getInstalmentType().getInstalmentType().getNumberOfMonths();
        double nominalPerMonth = loanTransaction.getNominal() / numberOfMonths;
        LoanTransactionDetail newTransactionDetail = LoanTransactionDetail.builder()
                .transactionDate(new Date().getTime())
                .nominal(nominalPerMonth + (nominalPerMonth * request.getInterestRates()))
                .loanTransaction(loanTransaction)
                .loanStatus(LoanStatus.PAID)
                .createdAt(new Date().getTime())
                .updatedAt(new Date().getTime())
                .build();

        foundTransactionDetails.add(newTransactionDetail);
        loanTransactionDetailService.saveBulk(foundTransactionDetails);
        loanTransaction.setLoanTransactionDetails(foundTransactionDetails);
    }
}
