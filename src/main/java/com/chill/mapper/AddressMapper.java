package com.chill.mapper;

import com.chill.entity.Country;
import com.chill.entity.State;
import com.chill.entity.City;
import com.chill.entity.PostalCode;
import com.chill.entity.Address;
import com.chill.payload.request.AddressRequest;
import com.chill.payload.response.AddressResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class AddressMapper {

    public Address mapToAddress(AddressRequest addressRequest) {
        Country country = new Country(addressRequest.getCountry());
        State state = new State(addressRequest.getState(), country);
        City city = new City(addressRequest.getCity(), state);
        PostalCode postalCode = new PostalCode(addressRequest.getPostalCode(), city);
        return new Address(country, state, city, postalCode);
    }

    public AddressResponse mapToAddressResponse(Address address) {
        return new AddressResponse(address.getCountry().getName(), address.getState().getName(), address.getCity().getName(), address.getPostalCode().getName());
    }

    public String mapToString(Address address) {
        return address.getCountry().getName() + " " + address.getState().getName() + " " + address.getCity().getName() + " " + address.getPostalCode().getName();
    }

    public Address mapToAddress(String addressStr) {
        String[] addressArr = addressStr.split(" ");
        Country country = new Country(addressArr.length > 0 ? addressArr[0] : "");
        State state = new State(addressArr.length > 1 ? addressArr[1] : "", country);
        City city = new City(addressArr.length > 2 ? addressArr[2] : "", state);
        PostalCode postalCode = new PostalCode(addressArr.length > 3 ? addressArr[3] : "", city);
        return new Address(country, state, city, postalCode);
    }
}
