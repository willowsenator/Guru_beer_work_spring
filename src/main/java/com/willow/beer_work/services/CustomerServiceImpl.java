package com.willow.beer_work.services;

import com.willow.beer_work.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        return CustomerDto.builder().id(UUID.randomUUID()).name("Omar Fernando Moreno Benito").build();
    }

    @Override
    public CustomerDto save(CustomerDto customer) {
        return CustomerDto.builder().id(UUID.randomUUID()).name(customer.getName()).build();
    }

    @Override
    public void update(UUID customerId, CustomerDto customer) {
        // TODO - modify customer
    }

    @Override
    public void deleteById(UUID customerId) {
        log.debug("Deleting customer by Id...");
    }
}
