package com.enigma.livecode_loan_app.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApproveTransactionRequest {
    @NotBlank(message = "loanTransactionId is required")
    private String loanTransactionId;

    @NotNull(message = "interestRate is required")
    @Min(0)
    private Integer interestRates;
}
