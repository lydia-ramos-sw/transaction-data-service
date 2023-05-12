package com.smallworldfs.transactiondataservice.transaction.db.mapper;

import static com.smallworldfs.transactiondataservice.transaction.Transactions.newTransaction;
import static com.smallworldfs.transactiondataservice.transaction.Transactions.newTransactionNoId;
import static org.assertj.core.api.Assertions.assertThat;

import com.smallworldfs.starter.relationaldbtest.RelationalDbTest;
import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@RelationalDbTest(classes = TransactionMapper.class, additionalMigrationLocations = "db/data")
public class TransactionMapperTest {

    @Autowired
    private TransactionMapper mapper;

    @Nested
    class FindById{
        @Test
        void return_empty_when_transaction_does_not_exist() {
            Optional<Transaction> transaction = mapper.findById(55);
            assertThat(transaction).isEmpty();
        }

        @Test
        void returns_transaction_data_when_transaction_exists() {
            Optional<Transaction> transaction = mapper.findById(1);
            assertThat(transaction)
                    .isPresent()
                    .hasValue(newTransaction());

        }
    }


    @Nested
    class Insert{
        @Test
        void returns_transaction_data_when_transaction_create() {
            Transaction newTransaction = newTransactionNoId();
            mapper.insert(newTransaction);
            // Optional<Transaction> transaction = mapper.findById(2);
            Assertions.assertThat(newTransaction.getTransactionId()).isEqualTo(2);
        }
    }

    @Nested
    class Update{
        @Test
        void returns_transaction_data_when_transaction_update() {
            Transaction newTransaction = newTransaction();
            Integer result = mapper.update(1, newTransaction);
            Assertions.assertThat(result.intValue()).isEqualTo(1);
        }
    }

    @Nested
    class findComplianceData{
        @Test
        void return_zero_when_search_txn_in_progress_for_customerId_55() {
            Integer result = mapper.findTransactionsInProgressByCustomerId(55);
            Assertions.assertThat(result.intValue()).isEqualTo(0);
        }

        @Test
        void return_100_when_search_total_amount_sent_by_customerId_1() {
            Double result = mapper.findTotalAmountSentByCustomerId(1);
            Assertions.assertThat(result.intValue()).isEqualTo(100);
        }
    }
}
