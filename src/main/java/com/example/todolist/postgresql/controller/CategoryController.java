package com.example.todolist.postgresql.controller;

import com.example.todolist.exception.CategoryNotExistException;
import com.example.todolist.postgresql.model.Category;
import com.example.todolist.postgresql.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todo/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAll());
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getById(categoryId));
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody @Valid Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addOrUpdate(category));
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody @Valid Category category) {
        Category targetedCategory = categoryService.getById(categoryId);
        if (targetedCategory != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(categoryService.addOrUpdate(category));
        } else {
            throw new CategoryNotExistException("Category entity does not exist");
        }
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteById(categoryId);
        return ResponseEntity.noContent().build();
    }
}
