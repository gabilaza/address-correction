package com.b2.controller;

import com.b2.entity.Address;
import com.b2.payload.request.AddressRequest;
import com.b2.payload.response.AddressResponse;
import com.b2.mapper.AddressMapper;
import com.b2.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping(path = "/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressMapper addressMapper;

    private final AddressService addressService;

    @ResponseBody
    @GetMapping("/correct")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Correct an Address",
            tags = {"Address"},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        content =
                                @Content(
                                        schema = @Schema(implementation = AddressRequest.class),
                                        mediaType = "application/json")),
                @ApiResponse(responseCode = "400", content = @Content(schema = @Schema())),
            })
    public AddressResponse getAppointmentById(@Valid @RequestBody AddressRequest addressRequest) {
        return new AddressResponse("country", "state", "city", 100);
    }

}
