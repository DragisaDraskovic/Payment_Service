package com.backend.intern.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

    private String accountId; //account of the one who pays
    private float amount;
    private String bookingDate;
    private String currency;
    private String purposeCode;
    private String valueDate;
    private String operationId;
    private String creditorId;
    private String creditorName;
    private String creditorBic;
    CreditorAccount creditorAccount;
    private String debtorName;
    private String debtorBic;
    DebtorAccount debtorAccount;

}
