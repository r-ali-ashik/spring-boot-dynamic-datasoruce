package com.rali.controller;

import com.rali.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/{bank}/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getEmployees(@PathVariable String bank) {
        log.info("api call initiated for bank name: " + bank);
        return ResponseEntity.ok(employeeRepository.findAll());
    }
}