package com.example.spring.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Optional<Product> getById(Long id) {
        return repo.findById(id);
    }

    public Product create(Product product) {
        return repo.save(product);
    }

    public Product update(Product updatedProduct) {

        return repo.save(updatedProduct);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public ProductService(){}

}
