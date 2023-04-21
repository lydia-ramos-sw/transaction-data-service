package com.smallworldfs.transactiondataservice.transaction.service;

import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_COULD_NOT_BE_CREATED;
import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_COULD_NOT_BE_UPDATED;
import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_NOT_FOUND;

import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.db.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper mapper;

    public Transaction getTransaction(int id) {
        return mapper.findById(id)
                .orElseThrow(() -> TRANSACTION_NOT_FOUND.withParameters(id).asException());
    }

    public Transaction createTransaction(Transaction transaction) {
        return mapper.insert(transaction)
                .orElseThrow(() -> TRANSACTION_COULD_NOT_BE_CREATED.asException());
    }

    public void updateTransaction(int id, Transaction transaction) {
        mapper.update(id, transaction)
                .orElseThrow(() -> TRANSACTION_COULD_NOT_BE_UPDATED.withParameters(id).asException());
    }
}
