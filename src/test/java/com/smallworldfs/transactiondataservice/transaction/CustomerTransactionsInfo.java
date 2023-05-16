package com.smallworldfs.transactiondataservice.transaction;

import com.smallworldfs.transactiondataservice.transaction.db.entity.CustomerTransactionInfo;

public class CustomerTransactionsInfo {

    public static CustomerTransactionInfo newCustomerTransactionInfo() {
        return CustomerTransactionInfo.builder()
                .numberOfTxnInProgress(1)
                .aggregatedAmountSentInPeriod(100.0)
                .build();
    }
}
