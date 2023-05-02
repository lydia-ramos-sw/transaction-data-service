package com.smallworldfs.transactiondataservice.transaction.service;

import static com.smallworldfs.error.issue.DefaultIssueType.INTERNAL_ERROR;
import static com.smallworldfs.error.issue.DefaultIssueType.NOT_FOUND;
import static com.smallworldfs.error.issue.DefaultIssueType.REQUEST_ERROR;
import static com.smallworldfs.transactiondataservice.transaction.Transactions.newTransaction;
import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_COULD_NOT_BE_CREATED;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.smallworldfs.error.exception.ApplicationException;
import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.db.mapper.TransactionMapper;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


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
            newTransaction.setSendingPrincipal(3700.0);
            whenTransactionIsCreatedThenReturnException(newTransaction);

            ApplicationException exception = assertThrows(ApplicationException.class, () ->
                    service.createTransaction(newTransaction));

            Assertions.assertThat(exception)
                    .hasMessage("Transaction could not be created")
                    .returns(REQUEST_ERROR, e -> e.getIssue().getType());
        }

        @Test
        void when_create_Transaction_Id_Is_2() {
            Transaction newTransaction = newTransaction();
            Transaction expectedTransaction = newTransaction();
            expectedTransaction.setTransactionId(2);
            whenTransactionIsCreatedThenReturnTransaction(newTransaction);

            service.createTransaction(newTransaction);

            Assertions.assertThat(expectedTransaction).isEqualTo(newTransaction);
        }

        @Test
        void throws_exception_when_transaction_cannot_be_updated() {
            Transaction newTransaction = newTransaction();
            whenTransactionIsUpdatedThenReturnZero(1, newTransaction);

            ApplicationException exception = assertThrows(ApplicationException.class, () -> service
                    .updateTransaction(1, newTransaction));

            Assertions.assertThat(exception)
                    .hasMessage("Transaction with id '1' could not be updated")
                    .returns(INTERNAL_ERROR, e -> e.getIssue().getType());
        }

        @Test
        void returns_transaction_count_when_updating_transaction() {
            Transaction newTransaction = newTransaction();
            whenTransactionIsUpdatedThenReturn(1, newTransaction);

            Integer result = service.updateTransaction(1, newTransaction);

            Assertions.assertThat(result.intValue()).isEqualTo(1);
        }

        private void whenTransactionIsQueriedReturnEmpty(int id) {
            when(mapper.findById(id)).thenReturn(Optional.empty());
        }

        private void whenTransactionIsQueriedThenReturn(int id, Transaction transaction) {
            when(mapper.findById(id)).thenReturn(Optional.ofNullable(transaction));
        }

        private void whenTransactionIsCreatedThenReturnTransaction(Transaction transaction) {
            Mockito.doAnswer((t) -> {
                transaction.setTransactionId(2);
                return transaction;
            }).when(mapper).insert(transaction);
        }

        private void whenTransactionIsCreatedThenReturnException(Transaction transaction) {
            Mockito.doThrow(TRANSACTION_COULD_NOT_BE_CREATED.asException()).when(mapper).insert(transaction);
        }

        private void whenTransactionIsUpdatedThenReturnZero(int id, Transaction transaction) {
            when(mapper.update(id, transaction)).thenReturn(0);
        }

        private void whenTransactionIsUpdatedThenReturn(int id, Transaction transaction) {
            when(mapper.update(id, transaction)).thenReturn(1);
        }
    }
}
