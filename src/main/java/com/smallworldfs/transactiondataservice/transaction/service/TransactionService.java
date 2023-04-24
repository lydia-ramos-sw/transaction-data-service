package com.smallworldfs.transactiondataservice.transaction.service;

import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_COULD_NOT_BE_CREATED;
import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_COULD_NOT_BE_PAID;
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
        Integer result = mapper.insert(transaction);
        if (result == null || result.intValue() < 1) {
            throw TRANSACTION_COULD_NOT_BE_CREATED.asException();
        }
        return transaction;
    }

    public Integer updateTransaction(int id, Transaction transaction) {
        Integer result = mapper.update(id, transaction);
        if (result == null || result.intValue() < 1) {
            throw TRANSACTION_COULD_NOT_BE_UPDATED.withParameters(id).asException();
        }
        return result;
    }

    public void payoutTransaction(Integer id) {
        throw TRANSACTION_COULD_NOT_BE_PAID.withParameters(id).asException();
    }
}
