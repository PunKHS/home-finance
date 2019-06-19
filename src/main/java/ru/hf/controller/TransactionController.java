package ru.hf.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.hf.model.Status;
import ru.hf.model.Transaction;
import ru.hf.model.User;
import ru.hf.model.View;
import ru.hf.service.TransactionService;
import ru.hf.service.UserService;
import ru.hf.util.NotFoundException;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final static Logger logger = Logger.getLogger(String.valueOf(CategoryController.class));

    @Value("${page.size.max}")
    private int maxSizeOfPage;

    private TransactionService transactionService;

    private UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Transaction transaction = transactionService.getById(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction " + id + " not found");
        }
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/")
    @JsonView(View.Id.class)
    public ResponseEntity<?> create(@RequestBody List<Transaction> transactionList) {
        for (Transaction transaction : transactionList) {
            transaction.setStatus(Status.ACTIVE);
            transactionService.save(transaction);
        }
        return new ResponseEntity<>("Transactions have been created successfully", new HttpHeaders(), HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping(value = "/")
    @JsonView(View.Id.class)
    public ResponseEntity<?> update(@RequestBody Transaction transaction) {
        Transaction temp = transactionService.getById(transaction.getId());
        if (temp == null) {
            throw new NotFoundException("Transaction [" + transaction.getId() + "] not found");
        }
        transaction.setCreated(temp.getCreated());
        transaction.setStatus(Status.ACTIVE);
        transactionService.save(transaction);
        logger.info("Transaction [" + transaction.getId() + "] has been updated successfully");
        return new ResponseEntity<>("Transaction [" + transaction.getId() + "] has been updated successfully", new HttpHeaders(), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Transaction transaction = transactionService.getById(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction [" + id + "] not found");
        }
        transactionService.delete(transaction);
        return new ResponseEntity<>("Transaction [" + transaction.getId() + "] has been deleted successfully", new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/get_all_for_username", params = {"userName"}, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getAllForUserName(@RequestParam("userName") String userName) {
        User user = userService.getByUsername(userName);
        if (user == null)
            throw new NotFoundException("User [" + userName + "] not found");
        List<Transaction> transactionList = transactionService.getByUser(user);
        if (transactionList.size() == 0)
            throw new NotFoundException("Events not found");
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }

    @GetMapping(value = "/get_all_for_username", params = {"userName", "page", "size"}, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getAllForUserName(@RequestParam("userName") String userName,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        if (size > maxSizeOfPage) size = maxSizeOfPage;
        User user = userService.getByUsername(userName);
        if (user == null)
            throw new NotFoundException("User [" + userName + "] not found");
        Page<Transaction> resultPage = transactionService.getByUser(user, page, size);
        if (page > resultPage.getTotalPages()) {
            throw new NotFoundException("Page not found");
        } else if (resultPage.getTotalElements() == 0)
            throw new NotFoundException("Events not found");
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }
}


