package com.savitech.fintab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.savitech.fintab.dto.ApplyForLoanDto;
import com.savitech.fintab.dto.LoanTypeDto;
import com.savitech.fintab.impl.LoanServiceImpl;

@RestController
@RequestMapping("loan")
public class LoanController {

    @Autowired
    private LoanServiceImpl loanServiceImpl;

    @PostMapping()
    public ResponseEntity<?> createLoanType(@RequestBody LoanTypeDto loanTypeModel){
        return loanServiceImpl.createLoanType(loanTypeModel);
    }

    @GetMapping()
    public ResponseEntity<?> allLoanType(Pageable pageable){
        return loanServiceImpl.allLoan(pageable);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<?> updateLoanType(@PathVariable String Id, @RequestBody LoanTypeDto loanTypeModel){
        return loanServiceImpl.updateLoanType(Id, loanTypeModel);
    }

    @PostMapping("/apply")
    public ResponseEntity<?> customerApplyForLoan(@RequestBody ApplyForLoanDto loan){
        return loanServiceImpl.applyForLoan(loan);
    }

    @GetMapping("/customer")
    public ResponseEntity<?> myLoan(Pageable pageable){
        return loanServiceImpl.myLoans(pageable);
    }

    @GetMapping("/breakdown/{loanId}")
    public ResponseEntity<?> viewLoanBreakDown(@PathVariable String loanId){
        return loanServiceImpl.viewLoanBreakdown(loanId);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> adminCustomerLoans(Pageable pageable){
        return loanServiceImpl.adminAllCustomerLoan(pageable);
    }

    @GetMapping("/admin/breakdown/{loanId}")
    public ResponseEntity<?> adminViewLoanBreakDown(@PathVariable String loanId){
        return loanServiceImpl.adminViewLoanBreakdown(loanId);
    }
    
    @GetMapping("/admin/approve/{loanId}")
    public ResponseEntity<?> approveLoan(@PathVariable String loanId){
        return loanServiceImpl.approveLoan(loanId);
    }
}
