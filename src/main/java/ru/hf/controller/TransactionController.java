package ru.hf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hf.model.Transaction;
import ru.hf.service.TransactionService;
import ru.hf.util.CustomError;

import java.util.logging.Logger;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final static Logger logger = Logger.getLogger(String.valueOf(CategoryController.class));

    private final int MAX_PAGE_SIZE = 10;

    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> add(@RequestBody Transaction transaction) {
        if (transaction != null) {
            Transaction result = transactionService.add(transaction);
            logger.info("Transaction [" + transaction.getTransID() + "] was successfully added");
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomError("Error"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get_all_for_username", params = {"userName", "page", "size"}, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getAllForUserName(@RequestParam("userName") String userName,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        if (size > MAX_PAGE_SIZE) size = MAX_PAGE_SIZE;
        Page<Transaction> resultPage = transactionService.getAllForUserName(userName, page, size);
        if (page > resultPage.getTotalPages()) {
            return new ResponseEntity<>(new CustomError("Page not found"), HttpStatus.NOT_FOUND);
        } else if (resultPage.getTotalElements() == 0)
            return new ResponseEntity<>(new CustomError("Events not found"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }
}
