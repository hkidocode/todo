package com.example.todolist.postgresql.service;

import com.example.todolist.exception.CategoryNotExistException;
import com.example.todolist.postgresql.model.Category;
import com.example.todolist.postgresql.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getById(Long categoryId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            return categoryRepository.findById(categoryId).get();
        } else {
            throw new CategoryNotExistException("Category entity does not exist");
        }
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category addOrUpdate(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(Long categoryId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new CategoryNotExistException("Category entity does not exist");
        }
    }
}
