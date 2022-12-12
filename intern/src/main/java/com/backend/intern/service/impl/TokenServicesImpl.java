package com.backend.intern.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class TokenServicesImpl {

    public String getToken() throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("faculty1053", "faculty"));


        String url = "https://api.mockbank.io/oauth/token";
        HttpHeaders headers = new HttpHeaders();

        headers.setBasicAuth("faculty1053","faculty");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        MultiValueMap<String,String> data = new LinkedMultiValueMap<>();
        data.add("username", "ljubovic.ra148.2018@uns.ac.rs");
        data.add("password", "levi9");
        data.add("grant_type", "password");

        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity(data, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseBodyJson = objectMapper.readTree(responseBody);

        JsonNode accessTokenJson = responseBodyJson.get("access_token");
        String access_token = accessTokenJson.asText();
        return access_token;

    }
}
