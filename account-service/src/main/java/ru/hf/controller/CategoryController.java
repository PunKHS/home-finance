package ru.hf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.hf.model.Category;
import ru.hf.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    // final static Logger logger = Logger.getLogger(CategoryController.class);

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/get_categories", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Category>> getAvailableCategories() {
        List<Category> result = categoryService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
