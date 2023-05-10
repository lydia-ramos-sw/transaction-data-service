package com.smallworldfs.transactiondataservice.transaction.db.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Integer transactionId;
    @NotNull
    private Double sendingPrincipal;
    @NotNull
    private Double payoutPrincipal;
    @NotNull
    private Double fees;
    @NotNull
    private Double commission;
    @NotNull
    private Double agentCommission;
    @NotNull
    private Integer senderId;
    @NotNull
    private Integer beneficiaryId;
    @NotNull
    // TODO: 10/05/2023  Review if there is @MaxLength for VARCHAR(10)
    private TransactionStatus status;
}
