package com.smallworldfs.transactiondataservice.transaction.db.mapper;

import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper{

    Optional<Transaction> findById(int id);
}
