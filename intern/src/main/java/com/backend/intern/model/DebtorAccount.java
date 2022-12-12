package com.backend.intern.model;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DebtorAccount {

    private String currency;
    private String iban;
    private String bban;
    private String pan;
    private String maskedPan;
    private String msisdn;
}
