package com.savitech.fintab.entity.impl;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

@Data
public class BulkPayment {
//    private String mode;

    private MultipartFile file;

    private String paymentType;

    private String debitedAccount;

    private String transactionPin;

    private String description;

    private String date;
}