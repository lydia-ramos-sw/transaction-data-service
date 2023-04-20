package com.smallworldfs.transactiondataservice.transaction.api;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smallworldfs.transactiondataservice.transaction.api.request.CreateTransactionRequest;
import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.manager.TransactionManager;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionManager transactionManager;

    public TransactionController(com.smallworldfs.transactiondataservice.transaction.manager.TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }


    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable Integer id) {
        return transactionManager.getTransaction(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Transaction> createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest) {
        Transaction transaction = transactionManager.createTransaction(createTransactionRequest);
        return ResponseEntity.
                created(URI.create("/transactions/"
                        + transaction.getTransactionId()))
                .body(transaction);
    }

    /*
    @DeleteMapping(path = "{id}")
    public void deleteTransaction(@PathVariable("id") int id) {
        service.deleteTransaction(id);
    }

    @PutMapping(path = "{id}") // revisar y comparar con el amigosCode
    public void updateTransaction(@PathVariable("id") int id,
                              @RequestParam(required = false) Double sendingPrincipal) {
        service.updateTransaction(id, sendingPrincipal);
    }
    */

}
