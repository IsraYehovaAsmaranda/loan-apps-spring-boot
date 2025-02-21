package com.enigma.livecode_loan_app.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTransactionResponse {
    private String id;
    private String loanTypeId;
    private String instalmentTypeId;
    private String customerId;
    private Double nominal;
    private Long approvedAt;
    private String approvedBy;
    private String approvalStatus;
    private List<LoanTransactionDetailResponse> transactionDetailResponses;
    private Long createdAt;
    private Long updatedAt;
}
