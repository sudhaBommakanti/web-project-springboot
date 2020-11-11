package com.example.spring.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public class ProductController {
    private ProductService productService;

    private List<Product> products;

    public ProductController(@Autowired ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAll() {
            return productService.getAll();
    }

    @GetMapping("/products/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/products")
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping("/products")
    public Product update(@RequestBody Product updatedProduct) {
        return productService.update(updatedProduct);
    }

    @DeleteMapping("/products")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}

