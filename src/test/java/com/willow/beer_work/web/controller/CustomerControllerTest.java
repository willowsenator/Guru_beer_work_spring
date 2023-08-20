package com.willow.beer_work.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.willow.beer_work.services.CustomerService;
import com.willow.beer_work.web.controller.utils.ConstrainedFields;
import com.willow.beer_work.web.model.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "willownsenator.dev")
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

        var result = mockMvc.perform(get("/api/v1/customer/{customerId}", validCustomer.getId().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is(validCustomer.getName())))
                .andDo(document("v1/customer",
                        getPathParametersSnippet(),
                        getResponseFieldsSnippet()
                ));

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
        var result = mockMvc.perform(put("/api/v1/customer/{customerId}", validCustomer.getId().toString())
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("v1/customer", getPathParametersSnippet(),
                        getRequestFieldsSnippet()));

        assertNotNull(result.andReturn().getResponse());
    }

    @Test
    void deleteById() throws Exception {

        var result = mockMvc.perform(delete("/api/v1/customer/{customerId}", validCustomer.getId().toString()))
                .andExpect(status().isNoContent())
                .andDo(document("v1/customer", getPathParametersSnippet()));
        assertNotNull(result.andReturn().getResponse());
    }

    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("id").description("Customer Id").type("UUID"),
                fieldWithPath("name").description("Customer name")
        );
    }

    private static RequestFieldsSnippet getRequestFieldsSnippet(){
        ConstrainedFields fields = new ConstrainedFields(CustomerDto.class);
        return requestFields(
                fields.withPath("id").description("Customer Id").type("UUID"),
                fields.withPath("name").description("Customer name")
        );
    }

    private static PathParametersSnippet getPathParametersSnippet() {
        return pathParameters(
                parameterWithName("customerId").description("Customer Id to get")
        );
    }
}