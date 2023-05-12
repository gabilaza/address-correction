package com.b2.controller;

import com.b2.entity.Address;
import com.b2.payload.request.AddressRequest;
import com.b2.payload.response.AddressResponse;
import com.b2.service.AddressService;
//import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.TestInstance.Lifecycle;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    //@Autowired
    //private ObjectMapper mapper;

    @Test
    void givenPatientRole_whenSignUp_thenCreated() throws Exception {
        //// given
        //String role = Role.ERole.ROLE_PATIENT.name();
        //userRegisterDto.setRole(role);

        //// when
        //RequestBuilder request = MockMvcRequestBuilders.post(signupEndpointPath)
        //        .content(mapper.writeValueAsString(userRegisterDto))
        //        .contentType(MediaType.APPLICATION_JSON)
        //        .accept(MediaType.APPLICATION_JSON);

        //// then
        //ResultMatcher expectedStatus = MockMvcResultMatchers.status().isCreated();
        //ResultActions resultActions = mockMvc.perform(request).andExpect(expectedStatus);

        //MvcResult result = resultActions.andReturn();
        //String contentAsString = result.getResponse().getContentAsString();

        //Executable readValue = () -> mapper.readValue(contentAsString, UserRegisterResponseDto.class);
        //Assertions.assertDoesNotThrow(readValue);
        //UserRegisterResponseDto userRegisterResponseDto = mapper.readValue(contentAsString, UserRegisterResponseDto.class);
        //Assertions.assertEquals(email, userRegisterResponseDto.getEmail());
        //Assertions.assertEquals(role, userRegisterResponseDto.getRole());
    }
}
