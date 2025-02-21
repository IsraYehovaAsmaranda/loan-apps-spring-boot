package com.enigma.livecode_loan_app.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayLoanTransactionRequest {
    private Integer interestRates;
}
