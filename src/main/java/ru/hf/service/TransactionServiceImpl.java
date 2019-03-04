package ru.hf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hf.model.Transaction;
import ru.hf.model.User;
import ru.hf.repository.TransactionRepository;
import ru.hf.repository.UserRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Transaction add(Transaction transaction) {
        return transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public Page<Transaction> getAllForUserName(String userName, int page, int size) {
        User user = userRepository.findByUsername(userName);
        return transactionRepository.getAllForUserName(PageRequest.of(page, size), user);
    }
}
