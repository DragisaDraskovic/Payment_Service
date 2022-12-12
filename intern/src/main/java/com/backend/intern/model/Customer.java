package com.backend.intern.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {

    private String fullName;
    private String username;
    private String password;
    private String address;
    private String city;
    private String countryCode;
    private String msisdn;
    private String type;
    private String zip;
}
