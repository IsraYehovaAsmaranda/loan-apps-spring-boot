package com.enigma.livecode_loan_app.controller;

import com.enigma.livecode_loan_app.entity.LoanType;
import com.enigma.livecode_loan_app.model.response.CommonResponse;
import com.enigma.livecode_loan_app.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/loan-types")
@RequiredArgsConstructor
public class LoanTypeController {
    private final LoanTypeService loanTypeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<LoanType>> createLoanType(@RequestBody LoanType loanType) {
        LoanType response = loanTypeService.createLoanType(loanType);

        CommonResponse<LoanType> commonResponse = CommonResponse.<LoanType>builder()
                .message("SUCCESSFULLY CREATED LOAN TYPE")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<LoanType>> getLoanTypeById(@PathVariable String id) {
        LoanType response = loanTypeService.getById(id);

        CommonResponse<LoanType> commonResponse = CommonResponse.<LoanType>builder()
                .message("SUCCESSFULLY FETCH LOAN TYPE BY ID")
                .data(response)
                .build();

        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<LoanType>>> getAllLoanTypes() {
        List<LoanType> loanTypes = loanTypeService.getAll();

        CommonResponse<List<LoanType>> commonResponse = CommonResponse.<List<LoanType>>builder()
                .message("SUCCESSFULLY FETCH LOAN")
                .data(loanTypes)
                .build();

        return ResponseEntity.ok(commonResponse);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<LoanType>> updateLoanType(@RequestBody LoanType loanType) {
        LoanType response = loanTypeService.update(loanType);

        CommonResponse<LoanType> commonResponse = CommonResponse.<LoanType>builder()
                .message("SUCCESSFULLY UPDATED LOAN TYPE")
                .data(response)
                .build();

        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<String>> deleteLoanType(@PathVariable String id) {
        loanTypeService.delete(id);

        CommonResponse<String> response = CommonResponse.<String>builder()
                .message("SUCCESSFULLY DELETED LOAN TYPE")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }
}
