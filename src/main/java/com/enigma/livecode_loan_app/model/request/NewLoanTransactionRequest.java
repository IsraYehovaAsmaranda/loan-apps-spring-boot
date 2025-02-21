package com.enigma.livecode_loan_app.model.request;

import com.enigma.livecode_loan_app.entity.Customer;
import com.enigma.livecode_loan_app.entity.InstalmentType;
import com.enigma.livecode_loan_app.entity.LoanType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewLoanTransactionRequest {
    @NotBlank(message = "loanType is required")
    private LoanType loanType;

    @NotBlank(message = "instalmentType is required")
    private InstalmentType instalmentType;

    @NotBlank(message = "customer is required")
    private Customer customer;

    @NotBlank(message = "nominal is required")
    private Double Nominal;
}
