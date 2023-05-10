package com.smallworldfs.transactiondataservice.transaction.api;

import com.smallworldfs.transactiondataservice.transaction.db.entity.CustomerTransactionInfo;
import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionService service;

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable Integer id) {
        return service.getTransaction(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return service.createTransaction(transaction);
    }

    @PostMapping("/{id}/payout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void payoutTransaction(@PathVariable Integer id) {
        service.payoutTransaction(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTransaction(@PathVariable Integer id, @RequestBody Transaction transaction) {
        service.updateTransaction(id, transaction);
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomerTransactionInfo getCustomerTransactionInfo(@PathVariable Integer customerId) {
        return service.getCustomerTransactionInfo(customerId);
    }
}
