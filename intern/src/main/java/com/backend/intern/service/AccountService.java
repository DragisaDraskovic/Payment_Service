package com.backend.intern.service;

import com.backend.intern.model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AccountService {

    String randomNumber();
    String doCreateAccount(String customerId, String fullname) throws JsonProcessingException;

}
