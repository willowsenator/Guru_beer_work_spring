package com.willow.beer_work.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.willow.beer_work.services.CustomerService;
import com.willow.beer_work.web.model.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private CustomerDto validCustomer;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        validCustomer = CustomerDto.builder().id(UUID.randomUUID()).name("Pepe").build();
    }

    @Test
    void getCustomer() throws Exception {
        given(customerService.getCustomerById(validCustomer.getId())).willReturn(validCustomer);

        var result = mockMvc.perform(get("/api/v1/customer/" + validCustomer.getId().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is(validCustomer.getName())));

        assertNotNull(result.andReturn().getResponse());
    }

    @Test
    void save() throws Exception {
        var json = mapper.writeValueAsString(validCustomer);
        given(customerService.save(any())).willReturn(validCustomer);

        var result = mockMvc.perform(post("/api/v1/customer/")
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/customer/" + validCustomer.getId().toString()));

        assertNotNull(result.andReturn().getResponse());
    }

    @Test
    void update() throws Exception {
        validCustomer.setId(UUID.randomUUID());
        var json = mapper.writeValueAsString(validCustomer);
        var result = mockMvc.perform(put("/api/v1/customer/" + validCustomer.getId().toString())
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertNotNull(result.andReturn().getResponse());
    }

    @Test
    void deleteById() throws Exception {

        var result = mockMvc.perform(delete("/api/v1/customer/" + validCustomer.getId().toString()))
                .andExpect(status().isNoContent());
        assertNotNull(result.andReturn().getResponse());
    }
}