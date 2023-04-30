package com.willow.beer_work.web.mappers;

import com.willow.beer_work.domain.Customer;
import com.willow.beer_work.web.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto dto);
    CustomerDto customerToCustomerDto(Customer customer);
}
