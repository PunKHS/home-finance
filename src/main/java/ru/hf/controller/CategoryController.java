package ru.hf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hf.model.Category;
import ru.hf.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/get_all", produces = "application/json")
    public ResponseEntity<List<Category>> getAll() {
        List<Category> result = categoryService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
