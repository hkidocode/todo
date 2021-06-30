package com.example.todolist.postgresql.service;

import com.example.todolist.exception.ProductNotExistException;
import com.example.todolist.postgresql.model.Product;
import com.example.todolist.postgresql.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getById(Long productId) {
        if (productRepository.findById(productId).isPresent()) {
            return productRepository.findById(productId).get();
        } else {
            throw new ProductNotExistException("Product entity does not exist");
        }
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product addOrUpdate(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long userId) {
        if (productRepository.findById(userId).isPresent()) {
            productRepository.deleteById(userId);
        } else {
            throw new ProductNotExistException("Product entity does not exist");
        }
    }
}
