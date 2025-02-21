package com.enigma.livecode_loan_app.service;

import com.enigma.livecode_loan_app.entity.Customer;
import com.enigma.livecode_loan_app.model.request.NewCustomerRequest;
import com.enigma.livecode_loan_app.model.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    public CustomerResponse createCustomer(NewCustomerRequest request);
    public CustomerResponse getById(String id);
    public List<CustomerResponse> getAll();
    public CustomerResponse update(Customer customer);
    public void delete(String id);
}
