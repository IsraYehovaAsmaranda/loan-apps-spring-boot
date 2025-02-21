package com.enigma.livecode_loan_app.model.response;

import com.enigma.livecode_loan_app.constant.LoanStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTransactionDetailResponse {
    private String id;
    private Long transactionDate;
    private Double nominal;
    private LoanStatus loanStatus;
    private Long createdAt;
    private Long updatedAt;
}
