package com.backend.intern.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

    private String bban;
    private String currency = "EUR";
    private String iban;
    private String name;
    private String type = "CASH";
}
