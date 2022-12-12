package com.backend.intern.service;

import com.backend.intern.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CustomerService {
    String doCreateCustomer(String email, String firstName, String lastName) throws JsonProcessingException;
}
