package com.example.todolist.postgresql.controller;

import com.example.todolist.exception.ProductNotExistException;
import com.example.todolist.postgresql.model.Product;
import com.example.todolist.postgresql.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todo/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAll());
    }

    @GetMapping("{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getById(productId));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addOrUpdate(product));
    }

    @PutMapping("{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody @Valid Product product) {
        Product targetedProduct = productService.getById(productId);
        if (targetedProduct != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.addOrUpdate(product));
        } else {
            throw new ProductNotExistException("Product entity does not exist");
        }
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }
}


