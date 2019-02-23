package ru.hf.service;

import org.springframework.data.domain.Page;
import ru.hf.model.Transaction;

public interface TransactionService {

    Transaction add(Transaction transaction);

    Page<Transaction> getAllForUserName(String userName, int page, int size);
}
