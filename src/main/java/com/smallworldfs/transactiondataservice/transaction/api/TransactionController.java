package com.smallworldfs.transactiondataservice.transaction.api;

import com.smallworldfs.transactiondataservice.transaction.db.entity.Transaction;
import com.smallworldfs.transactiondataservice.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
