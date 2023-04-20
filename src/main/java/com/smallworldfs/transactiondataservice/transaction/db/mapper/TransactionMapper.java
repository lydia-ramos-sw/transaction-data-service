package com.smallworldfs.transactiondataservice.transaction.db.mapper;

import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TransactionMapper {

    Optional<Transaction> findById(int id);

    void insertTransaction(@Param("transaction") Transaction transaction);

    //void deleteTransaction(int id);

    //void updateTransaction(Transaction transaction);
}
