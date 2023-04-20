package com.smallworldfs.transactiondataservice.transaction.manager;

import com.smallworldfs.transactiondataservice.transaction.api.request.CreateTransactionRequest;
import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.service.TransactionService;
import com.smallworldfs.transactiondataservice.transaction.service.mapper.TransactionMapperService;
import com.smallworldfs.transactiondataservice.transaction.service.vo.CreateTransactionVO;
import org.springframework.stereotype.Service;

@Service
public class TransactionManager{
    private final com.smallworldfs.transactiondataservice.transaction.service.TransactionService TransactionService;
    private final TransactionMapperService TransactionMapperService;

    public TransactionManager(TransactionService TransactionService, com.smallworldfs.transactiondataservice.transaction.service.mapper.TransactionMapperService TransactionMapperService) {
        this.TransactionService = TransactionService;
        this.TransactionMapperService = TransactionMapperService;
    }

    public Transaction getTransaction(Integer id) {
        return TransactionService.getTransaction(id);
    }

    public Transaction createTransaction(CreateTransactionRequest createTransactionRequest) {
        final CreateTransactionVO createTransactionVO = TransactionMapperService.convertRequestToVO(createTransactionRequest);
        return TransactionService.createTransaction(createTransactionVO);
    }
}
