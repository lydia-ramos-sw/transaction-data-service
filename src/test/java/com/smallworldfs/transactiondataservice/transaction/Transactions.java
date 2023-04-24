package com.smallworldfs.transactiondataservice.transaction;

import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.db.entity.TransactionStatus;

public class Transactions {
    public static Transaction newTransaction() {
        return Transaction.builder()
                .transactionId(1)
                .sendingPrincipal(100.0)
                .payoutPrincipal(98.0)
                .fees(2.0)
                .commission(1.8)
                .agentCommission(0.2)
                .senderId(3)
                .beneficiaryId(4)
                .status(TransactionStatus.NEW).build();
    }
    public static Transaction newTransactionNoId() {
        return Transaction.builder()
                .sendingPrincipal(100.0)
                .payoutPrincipal(98.0)
                .fees(2.0)
                .commission(1.8)
                .agentCommission(0.2)
                .senderId(3)
                .beneficiaryId(4)
                .status(TransactionStatus.NEW).build();
    }
}
