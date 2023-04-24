package com.smallworldfs.transactiondataservice.transaction.db.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;

@Mapper
public interface TransactionMapper {

    Optional<Transaction> findById(int id);

    /*
     * @Insert("INSERT INTO transaction(sending_principal, payout_principal, fees," +
     * " commission, agent_commission, sender_id, beneficiary_id," + " status) " +
     * " VALUES (#{transaction.sendingPrincipal}, #{transaction.payoutPrincipal}, " +
     * "#{transaction.fees}, #{transaction.commission}," +
     * " #{transaction.agentCommission}, #{transaction.senderId}, " +
     * "#{transaction.beneficiaryId}, #{transaction.status})")
     */
    Integer insert(Transaction transaction);

    @Update("Update transaction.transaction set sending_principal=#{transaction.sendingPrincipal}, "
            + " payout_principal=#{transaction.payoutPrincipal}, " +
            " fees=#{transaction.fees}, " +
            " commission=#{transaction.commission}, " +
            " agent_commission=#{transaction.agentCommission}, " +
            " sender_id=#{transaction.senderId}, " +
            " beneficiary_id=#{transaction.beneficiaryId}, " +
            " status=#{transaction.status} " +
            " where id=#{transaction.transactionId}")
    Integer update(int id, Transaction transaction);
}
