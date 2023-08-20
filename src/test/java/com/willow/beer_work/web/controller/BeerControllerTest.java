package com.willow.beer_work.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.willow.beer_work.services.BeerService;
import com.willow.beer_work.web.controller.utils.ConstrainedFields;
import com.willow.beer_work.web.model.BeerDto;
import com.willow.beer_work.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
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
        validBeer = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("beer1")
                .beerStyle(BeerStyleEnum.ALE)
        .build();

    }

    @Test
    void getBeer() throws Exception {
        given(beerService.getBeerById(validBeer.getId())).willReturn(validBeer);
        var result = mockMvc.perform(get("/api/v1/beer/{beerId}", validBeer.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
                .andDo(document("v1/beer",
                        getPathParametersSnippet(),
                        getResponseFieldsSnippet()
                ));

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
                        .andExpect(header().string("Location", "/api/v1/beer/" + validBeer.getId().toString()))
                .andDo(document("v1/beer",
                        getRequestFieldsSnippet()
                ));
        assertNotNull(result.andReturn().getResponse());
    }


    @Test
    void update() throws Exception {
        validBeer.setId(UUID.randomUUID());
        String json = mapper.writeValueAsString(validBeer);
        var result = mockMvc.perform(put("/api/v1/beer/{beerId}", validBeer.getId().toString())
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("v1/beer",
                        getPathParametersSnippet(),
                        getRequestFieldsSnippet()));

        assertNotNull(result.andReturn().getResponse());
    }

    @Test
    void deleteById() throws Exception {
        var result = mockMvc.perform(delete("/api/v1/beer/{beerId}", validBeer.getId().toString()))
                .andExpect(status().isNoContent())
                .andDo(document("v1/beer", getPathParametersSnippet()));

        assertNotNull(result.andReturn().getResponse());
    }

    private static RequestFieldsSnippet getRequestFieldsSnippet(){
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
        return requestFields(
                fields.withPath("id").ignored(),
                fields.withPath("version").description("api version").type("Integer"),
                fields.withPath("beerName").description("Beer name"),
                fields.withPath("createdDate").ignored(),
                fields.withPath("lastModifiedDate").ignored(),
                fields.withPath("beerStyle").description("Beer Style"),
                fields.withPath("upc").description("Beer UPC").type("Long"),
                fields.withPath("price").description("Price").type("BigDecimal"),
                fields.withPath("minOnHand").description("min beers on hand").type("Integer"),
                fields.withPath("quantityToBrew").description("amount of beers to brew").type("Integer")
        );
    }
    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("id").description("Beer id").type("UUID"),
                fieldWithPath("version").description("api version").type("Integer"),
                fieldWithPath("beerName").description("Beer name"),
                fieldWithPath("createdDate").description("Created Date").type("OffsetDateTime"),
                fieldWithPath("lastModifiedDate").description("last modified date").type("OffsetDateTime"),
                fieldWithPath("beerStyle").description("beer Style"),
                fieldWithPath("upc").description("Upc of beer").type("Long"),
                fieldWithPath("price").description("Price").type("BigDecimal"),
                fieldWithPath("minOnHand").description("Min beers on hand").type("Integer"),
                fieldWithPath("quantityToBrew").description("Quantity to brew").type("Integer")
        );
    }

    private static PathParametersSnippet getPathParametersSnippet() {
        return pathParameters(
                parameterWithName("beerId").description("beer UUID to get")
        );
    }
}