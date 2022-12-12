package com.backend.intern.service.impl;

import com.backend.intern.model.Account;
import com.backend.intern.model.Customer;
import com.backend.intern.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Random;

@Service
@AllArgsConstructor
@Transactional
public class CreateAccountServiceImpl implements AccountService {

    TokenServicesImpl tokenServices;

    public String randomNumber() {
        Random random = new Random();
        byte[] randomByte = new byte[18];
        random.nextBytes(randomByte);
        return random.toString();
    }


    public String doCreateAccount(String customerId, String fullname) throws JsonProcessingException {
        String token  = tokenServices.getToken();

        String url = "https://api.mockbank.io/customers/" + customerId + "/accounts";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // ovo je proba
        Account account = new Account();
        account.setName(fullname);
        account.setBban(randomNumber());
        account.setIban(randomNumber());


        HttpEntity<Account> request = new HttpEntity<>(account, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseBodyJson = objectMapper.readTree(responseBody);

        JsonNode accountIdOnBody = responseBodyJson.get("externalId");
        String accountId = accountIdOnBody.asText();
        // ovde negde bi trebalo da se pozove tennis i setuje plejeru akaunt ID
        return  accountId;
    }
}
