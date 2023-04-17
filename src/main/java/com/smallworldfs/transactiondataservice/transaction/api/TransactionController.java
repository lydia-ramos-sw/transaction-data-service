package com.smallworldfs.transactiondataservice.transaction.api;

import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionService service;

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable Integer id) {
        return service.getTransaction(id);
        //return null;
    }

    @PostMapping
    public void registerNewTransaction(@RequestBody Transaction transaction) {
        service.addNewTransaction(transaction);
    }

    @DeleteMapping(path = "{id}")
    public void deleteTransaction(@PathVariable("id") int id) {
        service.deleteTransaction(id);
    }

    @PutMapping(path = "{id}")
    public void updateTransaction(@PathVariable("id") int id,
                              @RequestParam(required = false) Double sendingPrincipal) {
        service.updateTransaction(id, sendingPrincipal);
    }
}
