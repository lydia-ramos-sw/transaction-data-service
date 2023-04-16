package com.smallworldfs.transactiondataservice.transaction.db.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.smallworldfs.starter.relationaldbtest.RelationalDbTest;
import com.smallworldfs.transactiondataservice.transaction.Transactions;
import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@RelationalDbTest(classes = TransactionMapper.class, additionalMigrationLocations = "db/data")
public class TransactionMapperTest {

    @Autowired
    private TransactionMapper mapper;

    @Nested
    class FindById {
        @Test
        void return_empty_when_transaction_dows_not_exist() {
            Optional<Transaction> transaction = mapper.findById(55);
            assertThat(transaction).isEmpty();
        }

        @Test
        void returns_transaction_data_when_transaction_exists() {
            Optional<Transaction> transaction = mapper.findById(1);
            assertThat(transaction)
                    .isPresent()
                    .hasValue(Transactions.newTransaction());

        }

    }
}
