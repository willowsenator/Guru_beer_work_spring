package com.willow.beer_work.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.willow.beer_work.services.BeerService;
import com.willow.beer_work.web.model.BeerDto;
import com.willow.beer_work.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
    void setup() {
        validBeer = BeerDto.builder().id(UUID.randomUUID()).beerName("beer1").beerStyle(BeerStyleEnum.ALE).build();

    }

    @Test
    void getBeer() throws Exception {
        given(beerService.getBeerById(validBeer.getId())).willReturn(validBeer);
        var result = mockMvc.perform(get("/api/v1/beer/" + validBeer.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())));

        assertNotNull(result.andReturn().getResponse());
    }

    @Test
    void save() throws Exception {
        String json = mapper.writeValueAsString(validBeer);
        given(beerService.save(ArgumentMatchers.any())).willReturn(validBeer);
        var result = mockMvc.perform(post("/api/v1/beer/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(header().string("Location", "/api/v1/beer/" + validBeer.getId().toString()));
        assertNotNull(result.andReturn().getResponse());
    }


    @Test
    void update() throws Exception {
        validBeer.setId(UUID.randomUUID());
        String json = mapper.writeValueAsString(validBeer);
        var result = mockMvc.perform(put("/api/v1/beer/" + validBeer.getId().toString())
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertNotNull(result.andReturn().getResponse());
    }

    @Test
    void deleteById() throws Exception {
        var result = mockMvc.perform(delete("/api/v1/beer/" + validBeer.getId().toString()))
                .andExpect(status().isNoContent());

        assertNotNull(result.andReturn().getResponse());
    }
}