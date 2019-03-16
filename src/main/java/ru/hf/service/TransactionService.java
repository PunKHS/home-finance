package ru.hf.service;

import org.springframework.data.domain.Page;
import ru.hf.model.Transaction;
import ru.hf.model.User;

import java.util.List;

public interface TransactionService {

    Transaction save(Transaction transaction);

    void delete(Transaction transaction);

    Transaction getTransactionById(Long id);

    List<Transaction> getAllForUserName(User user);

    Page<Transaction> getAllForUserName(User user, int page, int size);
}
