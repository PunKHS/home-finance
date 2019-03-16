package ru.hf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hf.model.Transaction;
import ru.hf.model.User;
import ru.hf.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionRepository.saveAndFlush(transaction);
    }

    @Override
    @Transactional
    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.getTransactionById(id);
    }

    @Override
    @Transactional
    public List<Transaction> getAllForUserName(User user) {
        return transactionRepository.getAllForUserName(user);
    }

    @Override
    @Transactional
    public Page<Transaction> getAllForUserName(User user, int page, int size) {
        return transactionRepository.getAllForUserName(PageRequest.of(page, size), user);
    }
}
