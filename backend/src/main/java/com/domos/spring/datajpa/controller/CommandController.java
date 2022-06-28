package com.domos.spring.datajpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.domos.spring.datajpa.model.Command;
import com.domos.spring.datajpa.model.Product;
import com.domos.spring.datajpa.repository.CommandRepository;
import com.domos.spring.datajpa.repository.ProductRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CommandController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommandRepository commandRepository;

    @GetMapping("/command")
    public ResponseEntity<List<Command>> getAllCommands() {
        List<Command> commands = new ArrayList<Command>();

        commandRepository.findAll().forEach(commands::add);

        if (commands.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(commands, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}/command")
    public ResponseEntity<List<Command>> getAllCommandsByProductId(@PathVariable(value = "productId") Long productId) {
        List<Command> commands = commandRepository.findCommandsByProductId(productId);
        return new ResponseEntity<>(commands, HttpStatus.OK);
    }

    @GetMapping("/command/{id}")
    public ResponseEntity<Command> getCommandsById(@PathVariable(value = "id") Long id) {
        Optional<Command> command = commandRepository.findById(id);

        return new ResponseEntity<>(command, HttpStatus.OK);
    }

    @GetMapping("/command/{commandId}/product")
    public ResponseEntity<List<Product>> getAllProductsByCommandId(@PathVariable(value = "commandId") Long commandId) {
        if (!commandRepository.existsById(commandId)) {
            throw new ResourceNotFoundException("Not found Command  with id = " + commandId);
        }

        List<Product> products = productRepository.findProductsByCommandId(commandId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/product/{productId}/command")
    public ResponseEntity<Command> addCommand(@PathVariable(value = "productId") Long productId, @RequestBody Command commandRequest) {
        Command command = productRepository.findById(productId).map(product -> {
            long commandId = commandRequest.getId();

            // Command is existed
            if (commandId != 0L) {
                Command _command = commandRepository.findById(commandId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Command with id = " + productId));
                product.addCommand(_command);
                productRepository.save(product);
                return _command;
            }

            // add and create new Command
            product.addCommand(commandRequest);
            return commandRepository.save(commandRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Product with id = " + productId));

        return new ResponseEntity<>(command, HttpStatus.CREATED);
    }


    @DeleteMapping("/product/{productId}/command/{commandId}")
    public ResponseEntity<HttpStatus> deleteCommandFromProduct(@PathVariable(value = "productId") Long productId, @PathVariable(value = "commandId") Long commandId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Product with id = " + productId));

        product.removeCommand(commandId);
        productRepository.save(product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/command/{id}")
    public ResponseEntity<HttpStatus> deleteCommand(@PathVariable("id") long id) {
        commandRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}