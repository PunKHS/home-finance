package ru.hf.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.hf.model.Transaction;
import ru.hf.model.User;
import ru.hf.model.View;
import ru.hf.service.TransactionService;
import ru.hf.util.NotFoundException;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final static Logger logger = Logger.getLogger(String.valueOf(CategoryController.class));

    @Value("${page.size.max}")
    private int maxSizeOfPage;

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserDetailsService userService;

    @Transactional
    @PostMapping(value = "/", produces = "application/json")
    @JsonView(View.Id.class)
    public ResponseEntity<?> add(@RequestBody List<Transaction> transactionList) {
        try {
            for (Transaction transaction : transactionList) {
                transactionService.save(transaction);
            }
            return new ResponseEntity<>(transactionList, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PutMapping(value = "/", produces = "application/json")
    @JsonView(View.Id.class)
    public ResponseEntity<?> update(@RequestBody Transaction transaction) {
        Transaction result = transactionService.save(transaction);
        logger.info("Transaction [" + transaction.getId() + "] was successfully updated");
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction " + id + " not found");
        }
        transactionService.delete(transaction);
        return new ResponseEntity<>("Deleted transaction [" + id + "]", HttpStatus.OK);
    }

    @GetMapping(value = "/get_all_for_username", params = {"userName"}, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getAllForUserName(@RequestParam("userName") String userName) {
        User user = (User) userService.loadUserByUsername(userName);
        if (user == null)
            throw new NotFoundException("User [" + userName + "] not found");
        List<Transaction> transactionList = transactionService.getAllForUserName(user);
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
        User user = (User) userService.loadUserByUsername(userName);
        if (user == null)
            throw new NotFoundException("User [" + userName + "] not found");
        Page<Transaction> resultPage = transactionService.getAllForUserName(user, page, size);
        if (page > resultPage.getTotalPages()) {
            throw new NotFoundException("Page not found");
        } else if (resultPage.getTotalElements() == 0)
            throw new NotFoundException("Events not found");
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }
}


