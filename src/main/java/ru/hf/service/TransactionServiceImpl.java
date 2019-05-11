package ru.hf.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hf.model.Status;
import ru.hf.model.Transaction;
import ru.hf.model.User;
import ru.hf.repository.TransactionRepository;

import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionRepository.saveAndFlush(transaction);
    }

    @Override
    @Transactional
    public void delete(Transaction transaction) {
        transaction.setStatus(Status.DELETED);
        transactionRepository.saveAndFlush(transaction);
        log.info("Transaction with id [{}] has been deleted successfully", transaction.getId());
    }

    @Override
    public Transaction getById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> getByUser(User user) {
        return transactionRepository.findByUser(user);
    }

    @Override
    public Page<Transaction> getByUser(User user, int page, int size) {
        return transactionRepository.findByUser(PageRequest.of(page, size), user);
    }
}
