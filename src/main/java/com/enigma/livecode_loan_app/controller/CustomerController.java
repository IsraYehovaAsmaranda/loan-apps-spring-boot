package com.enigma.livecode_loan_app.controller;

import com.enigma.livecode_loan_app.entity.Customer;
import com.enigma.livecode_loan_app.model.request.NewCustomerRequest;
import com.enigma.livecode_loan_app.model.response.CommonResponse;
import com.enigma.livecode_loan_app.model.response.CustomerResponse;
import com.enigma.livecode_loan_app.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<CommonResponse<CustomerResponse>> createCustomer(@Valid @RequestBody NewCustomerRequest request) {
        CustomerResponse customerResponse = customerService.createCustomer(request);

        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("SUCCESSFULLY CREATED CUSTOMER")
                .data(customerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getById(id);

        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("SUCCESSFULLY FETCH CUSTOMER BY ID")
                .data(customerResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomers() {
        List<CustomerResponse> customerResponses = customerService.getAll();

        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .message("SUCCESSFULLY FETCH CUSTOMER")
                .data(customerResponses)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@RequestBody Customer customer) {
        CustomerResponse customerResponse = customerService.update(customer);

        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("SUCCESSFULLY UPDATED CUSTOMER")
                .data(customerResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<CommonResponse<String>> deleteCustomer(@PathVariable String id) {
        customerService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .message("Successfully Deleted Customer")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }
}
