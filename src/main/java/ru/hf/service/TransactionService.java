package ru.hf.service;

import org.springframework.data.domain.Page;
import ru.hf.model.Transaction;
import ru.hf.model.User;

import java.util.List;

public interface TransactionService {

    Transaction save(Transaction transaction);

    void delete(Transaction transaction);

    Transaction getById(Long id);

    List<Transaction> getByUser(User user);

    Page<Transaction> getByUser(User user, int page, int size);
}
