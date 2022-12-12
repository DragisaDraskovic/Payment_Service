package com.backend.intern.service.impl;

import com.backend.intern.model.Customer;
import com.backend.intern.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
@AllArgsConstructor
@Transactional
@Builder
public class CreateCustomerServiceImpl implements CustomerService {

    CreateAccountServiceImpl createAccountService;
    TokenServicesImpl tokenServices;

    public String doCreateCustomer(String email, String firstName, String lastName) throws JsonProcessingException {
        String token  = new String();
        try {
            token = tokenServices.getToken();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String url = "https://api.mockbank.io/customers";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        Customer customer = new Customer();
        String fullName = firstName + lastName;
        customer.setUsername(email);
        customer.setPassword("root123");
        customer.setFullName(fullName);


        HttpEntity<Customer> request = new HttpEntity<>(customer, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseBodyJson = objectMapper.readTree(responseBody);

        JsonNode customerIdOnBody = responseBodyJson.get("externalId");
        String customerId = customerIdOnBody.asText();
        // ovde bi trebao da se pozove tennis i setuje customerId plajeru
        createAccountService.doCreateAccount(customerId,fullName);
        return customerId;
    }





}
