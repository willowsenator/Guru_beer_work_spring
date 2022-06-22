package com.willow.beer_work.web.controller;

import com.willow.beer_work.services.CustomerService;
import com.willow.beer_work.web.model.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable UUID customerId){
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> save(@RequestBody CustomerDto customer){
        var dto = customerService.save(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + dto.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable UUID customerId, @RequestBody CustomerDto customer){
        customerService.update(customerId, customer);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID customerId){
        customerService.deleteById(customerId);
    }
}
