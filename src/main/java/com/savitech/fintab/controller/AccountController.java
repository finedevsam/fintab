package com.savitech.fintab.controller;

import com.savitech.fintab.dto.BulkPaymentDto;
import com.savitech.fintab.dto.InternalAccountDto;
import com.savitech.fintab.dto.PayWithChannelDto;
import com.savitech.fintab.dto.SetPinDto;
import com.savitech.fintab.dto.TransferDto;
import com.savitech.fintab.entity.TransactionLogs;
import com.savitech.fintab.impl.AccountServiceImpl;
import com.savitech.fintab.impl.BulkPaymentServiceImpl;
import com.savitech.fintab.impl.CredentialServiceImpl;
import com.savitech.fintab.impl.InternalAccountServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private CredentialServiceImpl credentialService;

    @Autowired
    private BulkPaymentServiceImpl bulkPaymentService;

    @Autowired
    private InternalAccountServiceImpl internalAccountServiceImpl;

    @GetMapping()
    public ResponseEntity<?> account(){
        return accountService.myAccounts();
    }



    @GetMapping("/lookup/{accountNo}")
    public ResponseEntity<?> accountLookup(@PathVariable String accountNo){
        return accountService.accountLookUp(accountNo);
    }

    @PostMapping("/set-pin")
    public ResponseEntity<?> setPin(@RequestBody SetPinDto setPin){
        return credentialService.setTransactionPin(setPin);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> fundTransfer(@RequestBody TransferDto ft){
        return accountService.transfer(ft);
    }


    @PostMapping("/channel/validate")
    public ResponseEntity<?> verifyPayWithChannel(@RequestBody PayWithChannelDto channelModel){
        return accountService.verifyPayWithChannel(channelModel);
    }

    @PostMapping("/payment/bulk")
    public ResponseEntity<?> bulkTransfer(BulkPaymentDto bulk){
        return bulkPaymentService.bulkPayment(bulk);
    }

    @GetMapping("/payment/bulk")
    public List<?> allBatchPayment(Pageable pageable){
        return bulkPaymentService.allBatchPayment(pageable).toList();
    }

    @GetMapping("/payment/bulk/{batchId}")
    public List<?> viewBatchPayments(@PathVariable String batchId){
        return bulkPaymentService.viewBatchDetails(batchId);
    }

    @GetMapping("logs")
    public List<TransactionLogs> myTransactionLogs(Pageable pageable){
        return accountService.myTransactionLogs(pageable).toList();
    }

    // Internal Account manager
    
    @PostMapping("/internal")
    public ResponseEntity<?> createInternalAccount(@RequestBody InternalAccountDto internalAccountModel){
        return internalAccountServiceImpl.createInternalAccount(internalAccountModel);
    }
    
    @GetMapping("/internal")
    public ResponseEntity<?> allInternalAccounts(Pageable pageable){
        return internalAccountServiceImpl.allInternalAccount(pageable);
    }
}
