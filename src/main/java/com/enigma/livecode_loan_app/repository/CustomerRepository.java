package com.enigma.livecode_loan_app.repository;

import com.enigma.livecode_loan_app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
