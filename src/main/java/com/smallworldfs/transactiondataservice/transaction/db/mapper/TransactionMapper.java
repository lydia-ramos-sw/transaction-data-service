package com.smallworldfs.transactiondataservice.transaction.db.mapper;

import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TransactionMapper {

    Optional<Transaction> findById(int id);

    @Insert("INSERT INTO transactions(sending_principal, payout_principal, fees," +
            " commission, agent_commission, sender_id, beneficiary_id," +
            " status) " +
            " VALUES (#{sendingPrincipal}, #{payoutPrincipal}, #{fees}, #{commission}," +
            " #{agentCommission}, #{senderId}, #{beneficiaryId}, #{status})")
    Optional<Transaction> insert(Transaction transaction);

    @Update("Update transactions set sending_principal=#{sendingPrincipal}, " +
            " payout_principal=#{payoutPrincipal}, " +
            " fees=#{fees}, " +
            " commission=#{commission}, " +
            " agent_commission=#{agentCommission}, " +
            " sender_id=#{senderId}, " +
            " beneficiary_id=#{beneficiaryId}, " +
            " status=#{status}, " +
            " where id=#{id}")
    Optional<Transaction> update(int id, Transaction transaction);
}
