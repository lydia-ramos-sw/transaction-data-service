package com.smallworldfs.transactiondataservice.transaction.service;

import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_ALREADY_EXISTENT;
import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_NOT_FOUND;

import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.db.mapper.TransactionMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper mapper;

    public Transaction getTransaction(int id) {
        return mapper.findById(id)
                .orElseThrow(() -> TRANSACTION_NOT_FOUND.withParameters(id).asException());
    }

    public void addNewTransaction(Transaction transaction) {
        Optional<Transaction> transactionOptional = mapper.findById(transaction.getTransactionId());
        if (transactionOptional.isPresent()) {
            throw TRANSACTION_ALREADY_EXISTENT.withParameters(transaction.getTransactionId()).asException();
        }
        mapper.insertTransaction(transaction);
    }

    public void deleteTransaction(int id) {
        Optional<Transaction> transactionOptional = mapper.findById(id);
        if (transactionOptional.isPresent()) {
            mapper.deleteTransaction(id);
        } else {
            throw TRANSACTION_NOT_FOUND.withParameters(id).asException();
        }
    }

    @Transactional
    public void updateTransaction(int id, Double sendingPrincipal) {
        Transaction transaction = mapper.findById(id)
                .orElseThrow(() -> TRANSACTION_NOT_FOUND.withParameters(id).asException());

        transaction.setSendingPrincipal(sendingPrincipal);

        mapper.updateTransaction(transaction);
    }
}
