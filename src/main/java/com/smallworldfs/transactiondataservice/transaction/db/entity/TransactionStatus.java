package com.smallworldfs.transactiondataservice.transaction.db.entity;

public enum TransactionStatus {
    NEW("NEW"),
    PAID_OUT("PAID_OUT"),
    IN_PROGRESS("IN_PROGRESS");

    TransactionStatus(String anew) { }
}
