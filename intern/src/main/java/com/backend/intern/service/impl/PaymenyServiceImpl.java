package com.backend.intern.service.impl;

import com.backend.intern.model.CreditorAccount;
import com.backend.intern.model.DebtorAccount;
import com.backend.intern.model.Transaction;
import com.backend.intern.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.lang.runtime.ObjectMethods;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

@Service
@AllArgsConstructor
@Transactional
public class PaymenyServiceImpl implements PaymentService {

    TokenServicesImpl tokenServices;
    public String doPayment() {

        String accountId = "39b6c349-f2c7-41bb-ab0d-bbbba7d8ae07";
        String userId = "e564929e-5709-49cb-9680-0328a35ce143";

        //get authorization
        String token = new String();
        try {
            token = tokenServices.getToken();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String url = "https://api.mockbank.io/customers/" + userId + "/transactions"; //mockbank api

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        Transaction transaction = createNewTransaction(accountId);

        HttpEntity<Transaction> request = new HttpEntity<>(transaction, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        if(response.getStatusCode() == HttpStatus.CREATED) {
            return "Created";
        }
        return "Payment has not been made";

    }

    public Transaction createNewTransaction(String accountId){

        Transaction transaction = new Transaction();

        transaction.setAccountId(accountId); //account of the payer
        transaction.setAmount(-10);
        transaction.setBookingDate(LocalDate.now().toString());
        transaction.setCurrency("EUR");
        transaction.setValueDate(LocalDate.now().toString());
        transaction.setCreditorId("03759826-a0e9-4659-bb98-dd4028c621f3"); //one account for all incomes
        transaction.setCreditorName("Thomas Mustermann");
        transaction.setCreditorBic("MOCKDEXXXXX");
        transaction.setDebtorName("Pera");
        transaction.setDebtorBic("MOCKDEXXXXX");
        transaction.setPurposeCode("ACCT");

        byte[] array = new byte[10];
        new Random().nextBytes(array);
        String idTransactionString = new String(array, Charset.forName("UTF-8"));
        transaction.setOperationId("payforappointment " + idTransactionString ); //has to be unique

        CreditorAccount creditorAccount = new CreditorAccount();
        creditorAccount.setCurrency("EUR");
        creditorAccount.setIban("DE30 4815 1623 0000 0021 70");
        creditorAccount.setBban("481516230000002170");
        creditorAccount.setPan("ppp");
        creditorAccount.setMaskedPan("qqq");
        transaction.setCreditorAccount(creditorAccount);

        DebtorAccount debtorAccount = new DebtorAccount();
        debtorAccount.setCurrency("EUR");
        debtorAccount.setIban("RS29 4815 1623 0000 0021 71");
        debtorAccount.setBban("481516230000002171");
        debtorAccount.setPan("ppp");
        debtorAccount.setMaskedPan("qqq");
        transaction.setDebtorAccount(debtorAccount);

        return transaction;
    }
}
