package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.BankTransaction.CreateBankTransactionRequest;
import com.dtalk.ecosystem.entities.BankTransaction;
import com.dtalk.ecosystem.entities.ModePayment;
import com.dtalk.ecosystem.entities.StatusPayment;
import com.dtalk.ecosystem.entities.users.Designer;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.BankTransactionRepository;
import com.dtalk.ecosystem.repositories.DesignerRepository;
import com.dtalk.ecosystem.services.BankTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class BankTransactionServiceImpl implements BankTransactionService {
    private final BankTransactionRepository bankTransactionRepository;
    private final DesignerRepository designerRepository;

    @Override
    public List<BankTransaction> getAllTransactionsByDesigner(Long designerId) {
        Designer designer = designerRepository.findById(designerId)
                .orElseThrow(() -> new ResourceNotFoundException(" designer not found "+ designerId));
        return bankTransactionRepository.findBankTransactionsByDesignerEquals(designer);
    }

    @Override
    public BankTransaction createTransaction(CreateBankTransactionRequest request, Long idDesigner) {
        Designer designer = designerRepository.findById(idDesigner)
                .orElseThrow(() -> new ResourceNotFoundException(" designer not found "+ idDesigner));
        BankTransaction bankTransaction = BankTransaction.builder().bankName(request.getBankName()).rib(request.getRib()).modePayment(ModePayment.valueOf(request.getModePayment())).designer(designer).build();
        return bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public BankTransaction updateStatusTransaction(Long idTransaction , StatusPayment newStatus) {

       BankTransaction bankTransaction = bankTransactionRepository.findById(idTransaction)
               .orElseThrow(() -> new ResourceNotFoundException(" transaction not found "+ idTransaction));
        bankTransaction.setStatus(newStatus);

       return bankTransactionRepository.save(bankTransaction);
    }
}
