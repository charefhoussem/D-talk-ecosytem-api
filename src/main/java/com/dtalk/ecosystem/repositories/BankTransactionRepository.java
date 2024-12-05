package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.BankTransaction;
import com.dtalk.ecosystem.entities.users.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction,Long> {
    List<BankTransaction> findBankTransactionsByDesignerEquals(Designer designer);
}
