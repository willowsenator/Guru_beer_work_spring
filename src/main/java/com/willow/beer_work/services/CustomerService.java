package com.willow.beer_work.services;

import com.willow.beer_work.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto getCustomerById(UUID customerId);

    CustomerDto save(CustomerDto customer);

    void update(UUID customerId, CustomerDto customer);

    void deleteById(UUID customerId);
}
