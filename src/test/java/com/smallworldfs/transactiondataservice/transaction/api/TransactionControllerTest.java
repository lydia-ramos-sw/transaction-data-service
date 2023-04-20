package com.smallworldfs.transactiondataservice.transaction.api;

import static com.smallworldfs.starter.servicetest.error.ErrorDtoResultMatcher.errorDto;
import static com.smallworldfs.transactiondataservice.transaction.Transactions.newCreateTransactionRequest;
import static com.smallworldfs.transactiondataservice.transaction.Transactions.newTransaction;
import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_NOT_FOUND;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.smallworldfs.transactiondataservice.transaction.api.request.CreateTransactionRequest;
import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.manager.TransactionManager;


@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

    @MockBean
    private TransactionManager manager;

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
                    .andExpect(jsonPath("$.commission", Matchers.equalTo(1.8)))
                    .andExpect(jsonPath("$.agentCommission", Matchers.equalTo(0.2)))
                    .andExpect(jsonPath("$.senderId", Matchers.equalTo(3)))
                    .andExpect(jsonPath("$.beneficiaryId", Matchers.equalTo(4)))
                    .andExpect(jsonPath("$.status", Matchers.equalTo("NEW")));
        }

        @Test
        void inserts_new_transaction() throws Exception {
            whenTransactionIsInsertedThenReturnTransaction(newCreateTransactionRequest());

            addNewTransaction(
                    newCreateTransactionRequest())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.transactionId", Matchers.equalTo(1)))
                    .andExpect(jsonPath("$.sendingPrincipal", Matchers.equalTo(100.0)))
                    .andExpect(jsonPath("$.payoutPrincipal", Matchers.equalTo(98.0)))
                    .andExpect(jsonPath("$.fees", Matchers.equalTo(2.0)))
                    .andExpect(jsonPath("$.commission", Matchers.equalTo(1.8)))
                    .andExpect(jsonPath("$.agentCommission", Matchers.equalTo(0.2)))
                    .andExpect(jsonPath("$.senderId", Matchers.equalTo(3)))
                    .andExpect(jsonPath("$.beneficiaryId", Matchers.equalTo(4)))
                    .andExpect(jsonPath("$.status", Matchers.equalTo("NEW")));
        }

        private void whenTransactionIsQueriedThenReturnTransaction(int id, Transaction transaction) {
            when(manager.getTransaction(id)).thenReturn(transaction);
        }

        private void whenTransactionIsQueriedThenThrowNotFound(int id) {
            when(manager.getTransaction(id)).thenThrow(TRANSACTION_NOT_FOUND.withParameters(id).asException());
        }

        private void whenTransactionIsInsertedThenReturnTransaction(CreateTransactionRequest createTransactionRequest) {
           when(manager.createTransaction(createTransactionRequest)).thenReturn(newTransaction());
        }

        private ResultActions getTransaction(int id) throws Exception {
            return mockMvc.perform(MockMvcRequestBuilders.get("/transactions/{id}", id));
        }

        private ResultActions addNewTransaction(CreateTransactionRequest createTransactionRequest)  throws Exception {
            ResultActions perform = mockMvc.perform(MockMvcRequestBuilders
                    .post("/transactions/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new Gson().toJson(createTransactionRequest)));
            return perform;
        }
    }
}
