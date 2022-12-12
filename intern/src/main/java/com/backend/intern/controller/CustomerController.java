package com.backend.intern.controller;

import com.backend.intern.service.impl.CreateAccountServiceImpl;
import com.backend.intern.service.impl.CreateCustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;

@RestController
@RequestMapping("/createCustomers")
@AllArgsConstructor
public class CustomerController {

    private CreateCustomerServiceImpl createCustomerServiceService;


    @PostMapping("/createCustomer")
    public String create(String email, String firstName, String lastName) throws JsonProcessingException {
        String createCustomer = createCustomerServiceService.doCreateCustomer(email,firstName, lastName);
        return createCustomer;
    }

}
