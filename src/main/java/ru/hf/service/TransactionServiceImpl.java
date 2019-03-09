package ru.hf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hf.model.Transaction;
import ru.hf.model.User;
import ru.hf.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction add(Transaction transaction) {
        return transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public List<Transaction> getAllForUserName(User user) {
        return transactionRepository.getAllForUserName(user);
    }

    @Override
    public Page<Transaction> getAllForUserName(User user, int page, int size) {
        return transactionRepository.getAllForUserName(PageRequest.of(page, size), user);
    }
}
