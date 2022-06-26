package com.willow.beer_work.web.controller.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.willow.beer_work.services.v2.BeerServiceV2;
import com.willow.beer_work.web.model.v2.BeerDtoV2;
import com.willow.beer_work.web.model.v2.BeerStyleEnum;
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

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(BeerControllerV2.class)
class BeerControllerV2Test {

    private BeerDtoV2 validBeerV2;

    @MockBean
    private BeerServiceV2 beerServiceV2;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        validBeerV2 = BeerDtoV2.builder().id(UUID.randomUUID()).beerName("beer1").beerStyle(BeerStyleEnum.IPA).build();

    }

    @Test
    void getBeer() throws Exception {
        given(beerServiceV2.getBeerById(validBeerV2.getId())).willReturn(validBeerV2);
        var result = mockMvc.perform(get("/api/v2/beer/" + validBeerV2.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validBeerV2.getId().toString())));

        assertNotNull(result.andReturn().getResponse());
    }

    @Test
    void save() throws Exception {
        String json = mapper.writeValueAsString(validBeerV2);
        given(beerServiceV2.save(ArgumentMatchers.any())).willReturn(validBeerV2);
        var result = mockMvc.perform(post("/api/v2/beer/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v2/beer/" + validBeerV2.getId().toString()));
        assertNotNull(result.andReturn().getResponse());
    }


    @Test
    void update() throws Exception {
        validBeerV2.setId(UUID.randomUUID());
        String json = mapper.writeValueAsString(validBeerV2);
        var result = mockMvc.perform(put("/api/v2/beer/" + validBeerV2.getId().toString())
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertNotNull(result.andReturn().getResponse());
    }

    @Test
    void deleteById() throws Exception {
        var result = mockMvc.perform(delete("/api/v2/beer/" + validBeerV2.getId().toString()))
                .andExpect(status().isNoContent());

        assertNotNull(result.andReturn().getResponse());
    }
}