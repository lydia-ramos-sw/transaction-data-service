package com.smallworldfs.transactiondataservice.transaction.service.mapper;

import com.smallworldfs.transactiondataservice.transaction.api.request.CreateTransactionRequest;
import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.service.vo.CreateTransactionVO;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperService{
    public CreateTransactionVO convertRequestToVO(CreateTransactionRequest createTransactionRequest) {
        CreateTransactionVO createTransactionVO = new CreateTransactionVO();
        createTransactionVO.setSendingPrincipal(createTransactionRequest.getSendingPrincipal());
        createTransactionVO.setPayoutPrincipal(createTransactionRequest.getPayoutPrincipal());
        createTransactionVO.setFees(createTransactionRequest.getFees());
        createTransactionVO.setCommission(createTransactionRequest.getCommission());
        createTransactionVO.setAgentCommission(createTransactionRequest.getAgentCommission());
        createTransactionVO.setSenderId(createTransactionRequest.getSenderId());
        createTransactionVO.setBeneficiaryId(createTransactionRequest.getBeneficiaryId());
        return createTransactionVO;
    }

    public Transaction convertVOToEntity(CreateTransactionVO createTransactionVO) {
        Transaction transaction = new Transaction();
        transaction.setSendingPrincipal(createTransactionVO.getSendingPrincipal());
        transaction.setPayoutPrincipal(createTransactionVO.getPayoutPrincipal());
        transaction.setFees(createTransactionVO.getFees());
        transaction.setCommission(createTransactionVO.getCommission());
        transaction.setAgentCommission(createTransactionVO.getAgentCommission());
        transaction.setSenderId(createTransactionVO.getSenderId());
        transaction.setBeneficiaryId(createTransactionVO.getBeneficiaryId());
        return transaction;
    }
}
