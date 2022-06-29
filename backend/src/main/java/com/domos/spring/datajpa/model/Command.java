package com.domos.spring.datajpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "commands")
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "total_price")
    private int totalPrice = 0;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "commands")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();


    public Command() {
    }

    public long getId() {
        return id;
    }

    public Set<Product> getProducts() {
        return products;
    }
    public void setProducts(Set<Product> tutorials) {
        this.products = tutorials;
    }
    public void addProduct(Product product) {
        this.products.add(product);
        this.totalPrice = this.totalPrice + product.getPrice();
    }

}