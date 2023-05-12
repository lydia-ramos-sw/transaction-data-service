package com.smallworldfs.transactiondataservice.transaction.api;

import static com.smallworldfs.starter.servicetest.error.ErrorDtoResultMatcher.errorDto;
import static com.smallworldfs.transactiondataservice.transaction.CustomerTransactionsInfo.newCustomerTransactionInfo;
import static com.smallworldfs.transactiondataservice.transaction.Transactions.newTransaction;
import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.CUSTOMER_INFO_ERROR;
import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_COULD_NOT_BE_CREATED;
import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_COULD_NOT_BE_PAID;
import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_NOT_FOUND;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.smallworldfs.transactiondataservice.transaction.db.entity.CustomerTransactionInfo;
import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.service.TransactionService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

    @MockBean
    private TransactionService service;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class GetTransaction {

        @Test
        void returns_not_found_when_transaction_does_not_exist() throws Exception {
            whenTransactionIsQueriedThenThrowNotFound(77);

            getTransaction(77).andExpect(status().isNotFound())
                    .andExpect(errorDto()
                            .hasMessage("Transaction with id '77' could not be found")
                            .hasCode("TRANSACTION_NOT_FOUND"));
        }

        @Test
        void returns_transaction_data_when_transaction_exists() throws Exception {
            whenTransactionIsQueriedThenReturnTransaction(1, newTransaction());
            getTransaction(1)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.transactionId", Matchers.equalTo(1)))
                    .andExpect(jsonPath("$.sendingPrincipal", Matchers.equalTo(100.0)))
                    .andExpect(jsonPath("$.payoutPrincipal", Matchers.equalTo(98.0)))
                    .andExpect(jsonPath("$.fees", Matchers.equalTo(2.0)))
                    .andExpect(jsonPath("$.commission", Matchers.equalTo(1.6)))
                    .andExpect(jsonPath("$.agentCommission", Matchers.equalTo(0.4)))
                    .andExpect(jsonPath("$.senderId", Matchers.equalTo(3)))
                    .andExpect(jsonPath("$.beneficiaryId", Matchers.equalTo(4)))
                    .andExpect(jsonPath("$.status", Matchers.equalTo("NEW")));
        }
    }

    @Nested
    class CreateTransaction {

        @Test
        void returns_transaction_data_when_creating_it() throws Exception {
            Transaction newTransaction = newTransaction();
            whenTransactionIsCreatedThenReturnTransaction(newTransaction);
            createTransaction(newTransaction)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.transactionId", Matchers.equalTo(1)))
                    .andExpect(jsonPath("$.sendingPrincipal", Matchers.equalTo(100.0)))
                    .andExpect(jsonPath("$.payoutPrincipal", Matchers.equalTo(98.0)))
                    .andExpect(jsonPath("$.fees", Matchers.equalTo(2.0)))
                    .andExpect(jsonPath("$.commission", Matchers.equalTo(1.6)))
                    .andExpect(jsonPath("$.agentCommission", Matchers.equalTo(0.4)))
                    .andExpect(jsonPath("$.senderId", Matchers.equalTo(3)))
                    .andExpect(jsonPath("$.beneficiaryId", Matchers.equalTo(4)))
                    .andExpect(jsonPath("$.status", Matchers.equalTo("NEW")));
        }

        @Test
        void returns_cannot_be_created_when_transaction_cannot_be_created() throws Exception {
            Transaction newTransaction = newTransaction();
            whenTransactionIsCreatedThenThrowCannotBeCreated(newTransaction);
            createTransaction(newTransaction)
                    .andExpect(errorDto()
                            .hasMessage("Transaction could not be created")
                            .hasCode("TRANSACTION_COULD_NOT_BE_CREATED"));
        }
    }

    @Nested
    class UpdateTransaction {

        @Test
        void returns_void_when_update_it() throws Exception {
            whenTransactionIsUpdatedThenReturn(1, newTransaction());
            updateTransaction(1, newTransaction())
                    .andExpect(status().isNoContent());
        }

        @Test
        void returns_void_when_pay_it() throws Exception {
            whenTransactionIsPaidThenReturn(1);
            payoutTransaction(1)
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    class PayTransaction {

        @Test
        void returns_cannot_be_paid_when_transaction_cannot_be_paid() throws Exception {
            whenTransactionIsPaidThenThrowCannotBePaid(1);

            payoutTransaction(1)
                    .andExpect(errorDto()
                            .hasMessage("Transaction with id '1' could not be paid")
                            .hasCode("TRANSACTION_COULD_NOT_BE_PAID"));
        }
    }

    @Nested
    class GetCustomerTransactionInfo {

        @Test
        void returns_exception_when_customer_info_search_has_problems() throws Exception {
            whenCustomerTransactionInfoIsQueriedThenThrowNotFound(77);

            getCustomerTransactionInfo(77).andExpect(status().isNotFound())
                    .andExpect(errorDto()
                            .hasMessage("Problems when trying to recover info for customer with id '77'")
                            .hasCode("CUSTOMER_INFO_ERROR"));
        }

        @Test
        void returns_customer_transaction_info_when_info_exists() throws Exception {
            whenCustomerTransactionInfoIsQueriedThenReturnCustomerTransactionInfo(1, newCustomerTransactionInfo());

            getCustomerTransactionInfo(1)
                    .andExpect(jsonPath("$.numberOfTxnInProgress", Matchers.equalTo(1)))
                    .andExpect(jsonPath("$.aggregatedAmountSentInPeriod", Matchers.equalTo(100.0)));
        }


    }

    private void whenTransactionIsQueriedThenReturnTransaction(int id, Transaction transaction) {
        when(service.getTransaction(id)).thenReturn(transaction);
    }

    private void whenTransactionIsQueriedThenThrowNotFound(int id) {
        when(service.getTransaction(id)).thenThrow(TRANSACTION_NOT_FOUND.withParameters(id).asException());
    }

    private void whenTransactionIsCreatedThenReturnTransaction(Transaction transaction) {
        when(service.createTransaction(transaction)).thenReturn(transaction);
    }

    private void whenTransactionIsCreatedThenThrowCannotBeCreated(Transaction transaction) {
        Mockito.doThrow(TRANSACTION_COULD_NOT_BE_CREATED.asException())
                .when(service)
                .createTransaction(transaction);
    }

    private void whenTransactionIsUpdatedThenReturn(int id, Transaction transaction) {
        when(service.updateTransaction(id, transaction)).thenReturn(1);
    }

    private void whenTransactionIsPaidThenReturn(int id) {
        Mockito.doNothing().when(service).payoutTransaction(id);
    }

    private void whenTransactionIsPaidThenThrowCannotBePaid(int id) {
        Mockito.doThrow(TRANSACTION_COULD_NOT_BE_PAID.withParameters(id).asException())
                .when(service)
                .payoutTransaction(id);
    }

    private void whenCustomerTransactionInfoIsQueriedThenThrowNotFound(int id) {
        when(service.getCustomerTransactionInfo(id)).thenThrow(CUSTOMER_INFO_ERROR.withParameters(id).asException());
    }

    private void whenCustomerTransactionInfoIsQueriedThenReturnCustomerTransactionInfo(int customerId, CustomerTransactionInfo customerTransactionInfo) {
        when(service.getCustomerTransactionInfo(customerId)).thenReturn(customerTransactionInfo);
    }

    private ResultActions getTransaction(int id) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/transactions/{id}", id));
    }

    private ResultActions createTransaction(Transaction transaction) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(new Gson().toJson(transaction))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions updateTransaction(int id, Transaction transaction) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put("/transactions/{id}", id)
                .content(new Gson().toJson(transaction))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions payoutTransaction(int id) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/transactions/{id}/payout", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions getCustomerTransactionInfo(int customerId) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/transactions/{customerId}/", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

}
