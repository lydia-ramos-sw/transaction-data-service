package com.smallworldfs.transactiondataservice.transaction.api;

import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.db.entity.TransactionStatus;
import com.smallworldfs.transactiondataservice.transaction.service.TransactionService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.constraints.NotNull;

import static com.smallworldfs.starter.servicetest.error.ErrorDtoResultMatcher.errorDto;
import static com.smallworldfs.transactiondataservice.transaction.Transactions.newTransaction;
import static com.smallworldfs.transactiondataservice.transaction.error.TransactionIssue.TRANSACTION_NOT_FOUND;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest{

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
                    .andExpect(jsonPath("$.commission", Matchers.equalTo(1.8)))
                    .andExpect(jsonPath("$.agentCommission", Matchers.equalTo(0.2)))
                    .andExpect(jsonPath("$.senderId", Matchers.equalTo(3)))
                    .andExpect(jsonPath("$.beneficiaryId", Matchers.equalTo(4)))
                    .andExpect(jsonPath("$.status", Matchers.equalTo("NEW")));
        }

        private void whenTransactionIsQueriedThenReturnTransaction(int id, Transaction transaction) {
            when(service.getTransaction(id)).thenReturn(transaction);
        }
        private void whenTransactionIsQueriedThenThrowNotFound(int id) {
                when(service.getTransaction(id))
                        .thenThrow(TRANSACTION_NOT_FOUND.withParameters(id).asException());
        }

        private ResultActions getTransaction(int id) throws Exception {
            return mockMvc.perform(MockMvcRequestBuilders.get("/transactions/{id}", id));
        }
     }



}
