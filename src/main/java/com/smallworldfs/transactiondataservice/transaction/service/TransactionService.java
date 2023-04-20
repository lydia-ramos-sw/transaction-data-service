package com.smallworldfs.transactiondataservice.transaction.service;

import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_NOT_FOUND;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.db.mapper.TransactionMapper;
import com.smallworldfs.transactiondataservice.transaction.service.mapper.TransactionMapperService;
import com.smallworldfs.transactiondataservice.transaction.service.vo.CreateTransactionVO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper mapper;
    private final TransactionMapperService transactionMapperService;

    public Transaction getTransaction(int id) {
        return mapper.findById(id)
                .orElseThrow(() -> TRANSACTION_NOT_FOUND.withParameters(id).asException());
    }

    public Transaction createTransaction(CreateTransactionVO createTransactionVO) {
        final Transaction transaction = transactionMapperService.convertVOToEntity(createTransactionVO);
        mapper.insertTransaction(transaction);
        return transaction;
    }

    /*public void deleteTransaction(int id) {
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
    }*/
}
