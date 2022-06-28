package com.domos.spring.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domos.spring.datajpa.model.Command;

import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Long> {

    List<Command> findCommandsByProductId(Long productId);


}