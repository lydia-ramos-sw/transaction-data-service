package com.smallworldfs.transactiondataservice.transaction.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTransactionInfo{
    private Integer numberOfTxnInProgress;
    private Double aggregatedAmountSentInPeriod;
}
