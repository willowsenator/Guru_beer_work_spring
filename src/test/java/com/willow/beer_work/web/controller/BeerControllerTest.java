package com.willow.beer_work.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.willow.beer_work.services.BeerService;
import com.willow.beer_work.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    private BeerDto validBeer;

    @MockBean
    private BeerService beerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        validBeer = BeerDto.builder().id(UUID.randomUUID()).beerName("beer1").beerStyle("PAULANER").build();

    }

    @Test
    void getBeer() throws Exception {
        given(beerService.getBeerById(validBeer.getId())).willReturn(validBeer);
        mockMvc.perform(get("/api/v1/beer/" + validBeer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())));
    }

    @Test
    void save() throws Exception {
        String json = mapper.writeValueAsString(validBeer);
        given(beerService.save(ArgumentMatchers.any())).willReturn(validBeer);
        mockMvc.perform(post("/api/v1/beer/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/beer/" + validBeer.getId().toString()));
    }


    @Test
    void update() {
    }
}