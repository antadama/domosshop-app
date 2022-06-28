package com.domos.spring.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domos.spring.datajpa.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByCommandId(Long commandId);


}