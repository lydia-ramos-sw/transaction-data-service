package com.smallworldfs.transactiondataservice.transaction.api.request;

import com.smallworldfs.transactiondataservice.transaction.db.entity.TransactionStatus;
import lombok.Builder;

@Builder
public class CreateTransactionRequest{
    private Double sendingPrincipal;
    private Double payoutPrincipal;
    private Double fees;
    private Double commission;
    private Double agentCommission;
    private Integer senderId;
    private Integer beneficiaryId;
    private TransactionStatus status;

    public Double getSendingPrincipal() {
        return sendingPrincipal;
    }

    public void setSendingPrincipal(Double sendingPrincipal) {
        this.sendingPrincipal = sendingPrincipal;
    }

    public Double getPayoutPrincipal() {
        return payoutPrincipal;
    }

    public void setPayoutPrincipal(Double payoutPrincipal) {
        this.payoutPrincipal = payoutPrincipal;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getAgentCommission() {
        return agentCommission;
    }

    public void setAgentCommission(Double agentCommission) {
        this.agentCommission = agentCommission;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Integer beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CreateTransactionRequest{" +
                "sendingPrincipal=" + sendingPrincipal +
                ", payoutPrincipal=" + payoutPrincipal +
                ", fees=" + fees +
                ", commission=" + commission +
                ", agentCommission=" + agentCommission +
                ", senderId=" + senderId +
                ", beneficiaryId=" + beneficiaryId +
                ", status=" + status +
                '}';
    }
}
