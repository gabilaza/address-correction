package com.chill.controller;

import com.chill.entity.Address;
import com.chill.mapper.AddressMapper;
import com.chill.payload.request.AddressRequest;
import com.chill.payload.response.AddressResponse;
import com.chill.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressMapper addressMapper;

    private final AddressService addressService;

    @PostMapping("/correction")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = {"Address"})
    public AddressResponse getCorrectAddress(@Valid @RequestBody AddressRequest addressRequest) {
        Address address = addressMapper.mapToAddress(addressRequest);
        Address correctAddress = addressService.correctAddress(address);
        return addressMapper.mapToAddressResponse(correctAddress);
    }

}
