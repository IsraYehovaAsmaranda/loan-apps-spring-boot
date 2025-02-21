package com.enigma.livecode_loan_app.controller;

import com.enigma.livecode_loan_app.model.request.ApproveTransactionRequest;
import com.enigma.livecode_loan_app.model.request.NewLoanTransactionRequest;
import com.enigma.livecode_loan_app.model.request.PayLoanTransactionRequest;
import com.enigma.livecode_loan_app.model.response.CommonResponse;
import com.enigma.livecode_loan_app.model.response.LoanTransactionResponse;
import com.enigma.livecode_loan_app.service.LoanTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/transactions")
@RequiredArgsConstructor
public class LoanTransactionController {
    private final LoanTransactionService loanTransactionService;

    @PostMapping
    public ResponseEntity<CommonResponse<LoanTransactionResponse>> requestLoan(@RequestBody NewLoanTransactionRequest request) {
        LoanTransactionResponse loanTransactionResponse = loanTransactionService.requestLoan(request);

        CommonResponse<LoanTransactionResponse> response = CommonResponse.<LoanTransactionResponse>builder()
                .message("SUCCESSFULLY CREATED LOAN TRANSACTION")
                .data(loanTransactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<LoanTransactionResponse>> getTransactionById(@PathVariable String id) {
        LoanTransactionResponse loanTransactionResponse = loanTransactionService.getById(id);

        CommonResponse<LoanTransactionResponse> response = CommonResponse.<LoanTransactionResponse>builder()
                .message("SUCCESSFULLY FETCH LOAN TRANSACTION BY ID")
                .data(loanTransactionResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{adminId}/approve")
    public ResponseEntity<CommonResponse<LoanTransactionResponse>> approveTransactionRequestByAdminId(@PathVariable String adminId, @Valid @RequestBody ApproveTransactionRequest request){
        LoanTransactionResponse loanTransactionResponse = loanTransactionService.approveTransaction(request, adminId);

        CommonResponse<LoanTransactionResponse> response = CommonResponse.<LoanTransactionResponse>builder()
                .message("SUCCESSFULLY APPROVED LOAN TRANSACTION")
                .data(loanTransactionResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{trxId}/pay")
    public ResponseEntity<CommonResponse<String>> approveTransactionRequestByAdminId(@PathVariable String trxId, @Valid @RequestBody PayLoanTransactionRequest request){
        loanTransactionService.payInstalment(trxId, request);

        CommonResponse<String> response = CommonResponse.<String>builder()
                .message("SUCCESSFULLY APPROVED LOAN TRANSACTION")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }
}
