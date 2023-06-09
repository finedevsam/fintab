package com.savitech.fintab.util.loan;

import lombok.Data;

@Data
public class RepaymentBreakdownBasicLoan {
    private String paymentDate;
    private double repaymentAmount;
    private double interestAmount;
    private double principalAmount;
}
