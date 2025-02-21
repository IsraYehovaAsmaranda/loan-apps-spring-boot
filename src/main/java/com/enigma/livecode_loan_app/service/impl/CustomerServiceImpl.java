package com.enigma.livecode_loan_app.service.impl;

import com.enigma.livecode_loan_app.entity.AppUser;
import com.enigma.livecode_loan_app.entity.Customer;
import com.enigma.livecode_loan_app.model.request.NewCustomerRequest;
import com.enigma.livecode_loan_app.model.response.CustomerResponse;
import com.enigma.livecode_loan_app.model.response.UserResponse;
import com.enigma.livecode_loan_app.repository.AppUserRepository;
import com.enigma.livecode_loan_app.repository.CustomerRepository;
import com.enigma.livecode_loan_app.service.AppUserService;
import com.enigma.livecode_loan_app.service.CustomerService;
import com.enigma.livecode_loan_app.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public CustomerResponse createCustomer(NewCustomerRequest request) {
        AppUser appUser = appUserRepository.findById(request.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND"));

        Customer newCustomer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(DateUtil.parseDate(request.getDateOfBirth(), "yyyy-MM-dd"))
                .phone(request.getPhone())
                .status("1")
                .user(appUser)
                .build();

        Customer savedCustomer = customerRepository.saveAndFlush(newCustomer);

        return CustomerResponse.builder()
                .id(savedCustomer.getId())
                .firstName(savedCustomer.getFirstName())
                .lastName(savedCustomer.getLastName())
                .dateOfBirth(savedCustomer.getDateOfBirth().toString())
                .phone(savedCustomer.getPhone())
                .status(savedCustomer.getStatus())
                .build();
    }

    @Override
    public CustomerResponse getById(String id) {
        Customer foundCustomer = findCustomerByIdOrThrow(id);
        return CustomerResponse.builder()
                .id(foundCustomer.getId())
                .firstName(foundCustomer.getFirstName())
                .lastName(foundCustomer.getLastName())
                .status(foundCustomer.getStatus())
                .dateOfBirth(foundCustomer.getDateOfBirth().toString())
                .phone(foundCustomer.getPhone())
                .build();
    }

    private Customer findCustomerByIdOrThrow(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CUSTOMER NOT FOUND"));
    }

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> {
            return CustomerResponse.builder()
                    .id(customer.getId())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .dateOfBirth(customer.getDateOfBirth().toString())
                    .phone(customer.getPhone())
                    .status(customer.getStatus())
                    .build();
        }).toList();
    }

    @Override
    public CustomerResponse update(Customer customer) {
        findCustomerByIdOrThrow(customer.getId());
        Customer updatedCustomer = customerRepository.saveAndFlush(customer);
        return CustomerResponse.builder()
                .id(updatedCustomer.getId())
                .firstName(updatedCustomer.getFirstName())
                .lastName(updatedCustomer.getLastName())
                .dateOfBirth(updatedCustomer.getDateOfBirth().toString())
                .phone(updatedCustomer.getPhone())
                .status(updatedCustomer.getStatus())
                .build();
    }

    @Override
    public void delete(String id) {
        Customer foundCustomer = findCustomerByIdOrThrow(id);
        foundCustomer.setStatus("0");

        customerRepository.saveAndFlush(foundCustomer);
    }
}
