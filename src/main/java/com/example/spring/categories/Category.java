package com.example.spring.categories;

import com.example.spring.products.Product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Category {
    @Id
    private Long id;
    @OneToMany
    private List<Product> products;

}
