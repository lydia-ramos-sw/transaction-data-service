package com.smallworldfs.transactiondataservice.transaction.service;

import static com.smallworldfs.error.issue.DefaultIssueType.INTERNAL_ERROR;
import static com.smallworldfs.error.issue.DefaultIssueType.NOT_FOUND;
import static com.smallworldfs.transactiondataservice.transaction.Transactions.newTransaction;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smallworldfs.error.exception.ApplicationException;
import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.db.mapper.TransactionMapper;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionMapper mapper;
    @InjectMocks
    private TransactionService service;

    @Nested
    class GetTransaction {

        @Test
        void throws_exception_when_transaction_does_not_exist() {
            whenTransactionIsQueriedReturnEmpty(55);

            ApplicationException exception = assertThrows(ApplicationException.class, () -> service.getTransaction(55));

            Assertions.assertThat(exception)
                    .hasMessage("Transaction with id '55' could not be found")
                    .returns(NOT_FOUND, e -> e.getIssue().getType());
        }

        @Test
        void returns_transaction_data_when_transaction_exists() {
            whenTransactionIsQueriedThenReturn(1, newTransaction());

            Transaction transaction = service.getTransaction(1);

            Assertions.assertThat(transaction).isEqualTo(newTransaction());
        }

        @Test
        void throws_exception_when_transaction_cannot_be_created() {
            Transaction newTransaction = newTransaction();
            whenTransactionIsCreatedThenReturnEmpty(newTransaction);

            ApplicationException exception = assertThrows(ApplicationException.class, () -> service.createTransaction(newTransaction));

            Assertions.assertThat(exception)
                    .hasMessage("Transaction could not be created")
                    .returns(INTERNAL_ERROR, e -> e.getIssue().getType());
        }

        @Test
        void returns_transaction_data_when_creating_transaction() {
            Transaction newTransaction = newTransaction();
            whenTransactionIsCreatedThenReturn(newTransaction());

            Transaction transaction = service.createTransaction(newTransaction);

            Assertions.assertThat(transaction).isEqualTo(newTransaction());
        }

        @Test
        void throws_exception_when_transaction_cannot_be_updated() {
            Transaction newTransaction = newTransaction();
            whenTransactionIsUpdatedThenReturnEmpty(1, newTransaction());

            ApplicationException exception = assertThrows(ApplicationException.class, () -> service.updateTransaction(1, newTransaction));

            Assertions.assertThat(exception)
                    .hasMessage("Transaction with id '1' could not be updated")
                    .returns(INTERNAL_ERROR, e -> e.getIssue().getType());
        }

        private void whenTransactionIsQueriedReturnEmpty(int id) {
            when(mapper.findById(id)).thenReturn(Optional.empty());
        }

        private void whenTransactionIsQueriedThenReturn(int id, Transaction transaction) {
            when(mapper.findById(id)).thenReturn(Optional.ofNullable(transaction));
        }

        private void whenTransactionIsCreatedThenReturnEmpty(Transaction transaction) {
            when(mapper.insert(transaction)).thenReturn(Optional.empty());
        }

        private  void whenTransactionIsCreatedThenReturn(Transaction transaction) {
            when(mapper.insert(transaction)).thenReturn(Optional.ofNullable(transaction));
        }

        private void whenTransactionIsUpdatedThenReturnEmpty(int id, Transaction transaction) {
            when(mapper.update(id, transaction)).thenReturn(Optional.empty());
        }
    }
}
