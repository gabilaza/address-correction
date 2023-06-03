package com.chill.controller;

import static org.junit.jupiter.api.TestInstance.Lifecycle;

import com.chill.payload.request.AddressRequest;
import com.chill.payload.response.AddressResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class AddressControllerTest {
    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper mapper;

    private static final String versionEndpoints = "v1";
    private static final String addressCorrectionPath =
            "/api/" + versionEndpoints + "/address/correction";

    @Test
    void givenInvalidAddress_whenCorrectAddress_thenBadRequest() throws Exception {
        // given
        AddressRequest addressRequest = new AddressRequest();

        // when
        RequestBuilder request =
                MockMvcRequestBuilders.post(addressCorrectionPath)
                        .content(mapper.writeValueAsString(addressRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON);

        // then
        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isBadRequest();
        mockMvc.perform(request).andExpect(expectedStatus);
    }

    @Test
    void givenValidAddress_whenCorrectAddress_thenOkAndAddressResponseExpectedMatches()
            throws Exception {
        // given
        AddressRequest addressRequest = new AddressRequest("Romania", "Iasi", "Iasi", "700001");

        // when
        RequestBuilder request =
                MockMvcRequestBuilders.post(addressCorrectionPath)
                        .content(mapper.writeValueAsString(addressRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON);

        // then
        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isOk();
        ResultActions resultActions = mockMvc.perform(request).andExpect(expectedStatus);

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Executable readValue = () -> mapper.readValue(contentAsString, AddressResponse.class);
        Assertions.assertDoesNotThrow(readValue);
    }
}
