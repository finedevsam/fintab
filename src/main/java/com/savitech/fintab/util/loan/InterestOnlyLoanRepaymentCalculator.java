package com.savitech.fintab.util.loan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class InterestOnlyLoanRepaymentCalculator {
    // public static void main(String[] args) {
    //     double loanAmount = 10000; // Example loan amount
    //     double annualInterestRate = 5.0; // Example annual interest rate
    //     int interestOnlyPeriodMonths = 6; // Interest-only period in months
    //     int loanTermMonths = 12; // Loan term in months

    //     List<RepaymentBreakdown> breakdown = calculateInterestOnlyLoanRepayment(loanAmount, annualInterestRate, interestOnlyPeriodMonths, loanTermMonths);

    //     // Print the breakdown
    //     for (RepaymentBreakdown repayment : breakdown) {
    //         System.out.println("Payment Date: " + repayment.getPaymentDate() + ", Repayment Amount: $" + repayment.getRepaymentAmount()
    //                 + ", Principal Amount: $" + repayment.getPrincipalAmount() + ", Interest Amount: $" + repayment.getInterestAmount());
    //     }
    // }

    public List<InterestOnlyLoanBreakDown> calculateInterestOnlyLoanRepayment(double loanAmount, double annualInterestRate, int interestOnlyPeriodMonths, int loanTermMonths) {
        List<InterestOnlyLoanBreakDown> breakdown = new ArrayList<>();
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(annualInterestRate).divide(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = calculateMonthlyInterestPayment(loanAmount, monthlyInterestRate);
        BigDecimal remainingBalance = BigDecimal.valueOf(loanAmount);
        LocalDate startDate = LocalDate.now();

        // Interest-only period
        for (int i = 1; i <= interestOnlyPeriodMonths; i++) {
            LocalDate paymentDate = startDate.plusMonths(i);
            String formattedDate = paymentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            BigDecimal interestAmount = remainingBalance.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);
            InterestOnlyLoanBreakDown repayment = new InterestOnlyLoanBreakDown();
            repayment.setPaymentDate(formattedDate);
            repayment.setInterestAmount(interestAmount.doubleValue());
            repayment.setRepaymentAmount(monthlyPayment.doubleValue());
            repayment.setPrincipalAmount(0.0);
            breakdown.add(repayment);
        }

        // Principal and interest repayment period
        for (int i = interestOnlyPeriodMonths + 1; i <= loanTermMonths; i++) {
            LocalDate paymentDate = startDate.plusMonths(i);
            String formattedDate = paymentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            BigDecimal interestAmount = remainingBalance.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal principalAmount = monthlyPayment.subtract(interestAmount).setScale(2, RoundingMode.HALF_UP);
            remainingBalance = remainingBalance.subtract(principalAmount).setScale(2, RoundingMode.HALF_UP);

            InterestOnlyLoanBreakDown repayment = new InterestOnlyLoanBreakDown();
            repayment.setPaymentDate(formattedDate);
            repayment.setRepaymentAmount(monthlyPayment.doubleValue());
            repayment.setPrincipalAmount(principalAmount.doubleValue());
            repayment.setInterestAmount(interestAmount.doubleValue());
            breakdown.add(repayment);
        }

        return breakdown;
    }

    public static BigDecimal calculateMonthlyInterestPayment(double loanAmount, BigDecimal monthlyInterestRate) {
        BigDecimal interestPayment = BigDecimal.valueOf(loanAmount).multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);
        return interestPayment;
    }
}
