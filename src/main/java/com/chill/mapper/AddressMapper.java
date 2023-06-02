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

import java.util.List;

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

    public Address mapToAddress(List<String> addressStr) {
        Country country = new Country(addressStr.size() > 0 ? addressStr.get(0) : "");
        State state = new State(addressStr.size() > 1 ? addressStr.get(1) : "", country);
        City city = new City(addressStr.size() > 2 ? addressStr.get(2) : "", state);
        PostalCode postalCode = new PostalCode(addressStr.size() > 3 ? addressStr.get(3) : "", city);
        return new Address(country, state, city, postalCode);
    }
}
