package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.BankTransaction.CreateBankTransactionRequest;
import com.dtalk.ecosystem.entities.BankTransaction;
import com.dtalk.ecosystem.entities.StatusPayment;

import java.util.List;

public interface BankTransactionService {
    List<BankTransaction> getAllTransactionsByDesigner(Long designerId);
    BankTransaction createTransaction(CreateBankTransactionRequest request , Long idDesigner);
    BankTransaction updateStatusTransaction(Long idTransaction , StatusPayment newStatus);

}
